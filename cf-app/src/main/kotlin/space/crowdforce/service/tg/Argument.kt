package space.crowdforce.service.tg

enum class Argument(val argName: String) {
    OWNED_PROJECT_ID("ownedProjectId"),
    GOAL_NAME("goalName"),
    GOAL_DESCRIPTION("goalDescription");

    fun rawFrom(map: Map<String, String>): String? {
        return map.get(argName)
    }
}
