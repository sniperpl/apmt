package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.actions.CSRF_TOKEN
import com.cognifide.apmt.tests.ExampleTestCases
import com.cognifide.apmt.tests.ExampleUsers
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ExampleCreateAssetTest : CreateAssetTest(
    ExampleTestCases.ADD_ASSET
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ExampleUsers.values())

        stubFor(
            post(urlPathMatching("/content/dam/my-product/(images|screens)"))
                .willReturn(aResponse().withStatus(201))
        )
        stubFor(
            post(urlPathMatching("/content/dam/my-product/(images|screens)"))
                .withHeader(CSRF_TOKEN, equalTo(ExampleUsers.USER.username))
                .willReturn(aResponse().withStatus(500))
        )
    }
}
