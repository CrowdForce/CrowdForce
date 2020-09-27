package space.crowdforce.service.tg

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import space.crowdforce.domain.User.Companion.NULL_USER
import space.crowdforce.repository.UserRepository
import space.crowdforce.service.tg.command.*

@Component
class CommandProcessor(
        val commandContext: CommandContext,
        val userRepository: UserRepository,
        val argumentsValidator: ArgumentsValidator,
        commandList: List<Command>
) {

    companion object {
        val COMMAND_PREFIX = "/"

        private val log = LoggerFactory.getLogger(CommandProcessor::class.java)
    }

    val commands: Map<String, Command> = commandList.map { it.name() to it }.toMap()

    val nameMapper: Map<String, String> = mapOf(
            ProjectsGoalCommand.NAME to "Добавления цели проекта",
            ProjectsCommand.NAME to "Просмотр проекта",
            ProjectsGoalListCommand.NAME to "Список целей"
    )

    fun extractCommand(args: String, userContext: UserContext): Command? {
        val argsList = args.trim().split(" ").toMutableList()

        var commandStr = argsList[0]

        commandStr = if (commandStr.startsWith(COMMAND_PREFIX))
            commandStr.substring(COMMAND_PREFIX.length)
        else commandStr

        if(!commandStr.equals(userContext.lastCommand))
            userContext.invalidate()

        userContext.applyContext(parseParams(argsList.subList(1, argsList.size)))

        return commands[commandStr]
    }

    @Transactional
    fun execute(userTgId: String, args: String): Response {
        if (args.isBlank()) {
            log.warn("Args is blank. Execution is skipped")

            return Response("Args is blank. Execution is skipped")
        }

        val context = commandContext.getOrCreate(userTgId.toLong())
        val command = extractCommand(args, context) ?: return Response("Команда не найдена")

        context.command(command.name())

        return executeCommand(command, context, userTgId.toInt())
    }

    @Transactional
    fun executeText(userTgId: String, messageId: String, args: String): Response {
        if (args.isBlank()) {
            log.warn("Args is blank. Execution is skipped")
            return Response("Args is blank. Execution is skipped")
        }

        if(args.startsWith(COMMAND_PREFIX))
            return execute(userTgId, args)

        val context = commandContext.getOrCreate(userTgId.toLong())

        context.putText(messageId, args)

        val command = commands[context.lastCommand] ?: return Response("Команда не найдена")

        return executeCommand(command, context, userTgId.toInt())
    }

    fun executeEditedText(userTgId: String, messageId: String, args: String) {
        val context = commandContext.getOrCreate(userTgId.toLong())

        context.put(messageId, args)
    }

    private fun executeCommand(command: Command, context: UserContext, userTgId: Int): Response {
        try {
            val user = userRepository.findByTelegramId(userTgId) ?: NULL_USER

            val validate = argumentsValidator.validate(user, command.arguments(), context)

            val answer = if (validate != null)
                return prepareResponse(validate, context, command)
            else
                command.execute(user, context)

            return prepareResponse(answer, context, command)
        } catch (e: Exception) {
            log.error("Error during command execution", e)

            return Response("В момент выполнения произошла ошибка, попробуйте изменить параметры ввода")
        }
    }

    private fun prepareResponse(answer: CommandAnswer, context: UserContext, command: Command): Response {
        if (answer.status == AnswerStatus.FINISH)
            context.invalidate()

        val actions = answer.links.map {
            val command = commands[it.commandName] ?: command

            val argumentsFromContext = command.arguments()
                    .filter { context.value(it) != null }
                    .map { "${it.argName}=${context.value(it)}" }
                    .joinToString(separator = " ")

            val argumentsFromCommand = it.attributes
                    .map { "${it.first}=${it.second}" }
                    .joinToString(separator = " ")

            val viewName = it.viewName ?: nameMapper[command.name()] ?: command.name()

            viewName to "/${command.name()} $argumentsFromContext $argumentsFromCommand"
        }.toList()

        return Response(text = answer.text, actions = actions)
    }

    private fun parseParams(arguments: List<String>): MutableMap<String, String> {
        val args = arguments.iterator()

        val params = mutableMapOf<String, String>()

        while (args.hasNext()) {
            var next = args.next()

            if (next.contains("=")) {
                val arg = next.split("=")

                params[arg[0]] = arg[1]
            }
        }

        return params
    }
}

