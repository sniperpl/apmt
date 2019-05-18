import com.cognifide.apmt.Checks
import com.cognifide.apmt.PermissionTest

class ExampleTest : PermissionTest({

    registerUsers(Users.values())
    registerGroups(Groups.values())

    "add assets" {
        test = Checks::pathContainsUser
        addPath("/content/sites/author")
        addUser(Users.GLOBAL_AUTHOR)
    }

    "modify assets" {
        test = Checks::alwaysSuccess
        addPath("/content/sites/site")
        addUser(Users.GLOBAL_AUTHOR)
    }
})
