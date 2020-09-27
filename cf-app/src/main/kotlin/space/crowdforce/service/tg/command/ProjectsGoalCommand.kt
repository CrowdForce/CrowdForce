package space.crowdforce.service.tg.command

import org.springframework.stereotype.Service
import space.crowdforce.domain.Goal
import space.crowdforce.domain.User
import space.crowdforce.repository.GoalRepository
import space.crowdforce.service.tg.*
import space.crowdforce.service.tg.Argument.*
import java.time.LocalDateTime

@Service
class ProjectsGoalCommand(
        val goalRepository: GoalRepository
) : Command {

    val requiredArguments = listOf(OWNED_PROJECT_ID, GOAL_NAME, GOAL_DESCRIPTION)

    override fun execute(user: User, context: UserContext): CommandAnswer {
        val projectId = context.value(OWNED_PROJECT_ID)!!.toInt()
        val goalName = context.valueByLink(GOAL_NAME)!!
        val goalDescription = context.valueByLink(GOAL_DESCRIPTION)!!

        val goal = goalRepository.insert(projectId, goalName, goalDescription, 0, LocalDateTime.now())

        return CommandAnswer.finish(
                text = goalView(goal),
                links = Navigation.mainMenu()
        )
    }

    fun goalView(goal: Goal) : String {
        return """
            Цель создана
            Имя : ${goal.name}
            Прогресс : ${goal.progressBar}
            Описание : ${goal.description}
        """.trimIndent()
    }

    override fun name(): String {
        return NAME
    }

    override fun arguments(): List<Argument> {
        return requiredArguments
    }

    companion object {
        val NAME = "addGoal"
    }
}
