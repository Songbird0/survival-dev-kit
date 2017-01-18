package fr.songbird.sdk.probabuilder

import org.junit.Before
import org.junit.Test

/**
 * Created by anthony on 13/01/17.
 */
class FavorableCaseTest {

    @Test(expected = Exception::class)
    fun negative_favorable_case_percentage()
    {
        FavorableCase("my awesome item", "item content", -1)
    }

    @Test(expected = Exception::class)
    fun null_favorable_case_percentage()
    {
        FavorableCase("my awesome item", "item content", 0)
    }
}