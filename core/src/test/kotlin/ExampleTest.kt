import com.cognifide.apmt.Checks
import com.cognifide.apmt.PermissionTest

class ExampleTest : PermissionTest({

    registerUsers(Users.values())

    "add assets" {
        test = Checks::pathContainsUser
        addPath("/content/sites/publish")
        addPath("/content/sites/something")
        addUser(Users.GLOBAL_AUTHOR)
        addUser(Users.PL_AUTHOR)
    }

    "modify assets" {
        test = Checks::alwaysSuccess
        addPath("/content/sites/site")
        addUser(Users.GLOBAL_AUTHOR)
    }

})
