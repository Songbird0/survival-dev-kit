package fr.songbird.sdk

import fr.songbird.sdk.probabuilder.FavorableCase
import fr.songbird.sdk.probabuilder.ProbabilityManager
import org.junit.Test
import java.util.ArrayList
import kotlin.test.fail

/**
 * Tests de la classe ProbabilityManager.
 * @author anthony
 * @since 13/01/17.
 */
class ProbabilityManagerTest {

    @Test(expected = Exception::class)
    fun favorable_case_list_is_empty_test()
    {
        val fav_case_list = ArrayList<FavorableCase<String>>()
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list)
    }
}