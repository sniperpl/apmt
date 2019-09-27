package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.asset.CreateAsset
import com.cognifide.apmt.actions.asset.EditAsset
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.ApmtBaseTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit asset")
abstract class EditAssetTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance
    private var undoAction: Action? = null

    @DisplayName("User can edit assets")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource(ALLOWED)
    fun userCanEditAssets(user: User, path: String) {
        undoAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.prepare()

        EditAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit assets")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource(DENIED)
    fun userCannotEditAssets(user: User, path: String) {
        undoAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.prepare()

        EditAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }

    @AfterEach
    fun cleanup() {
        undoAction!!.undo()
    }
}