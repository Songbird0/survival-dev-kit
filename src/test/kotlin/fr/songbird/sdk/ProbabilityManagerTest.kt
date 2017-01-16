package fr.songbird.sdk

import fr.songbird.sdk.probabuilder.FavorableCase
import fr.songbird.sdk.probabuilder.ProbabilityManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.ArrayList
import kotlin.test.fail

/**
 * Tests de la classe ProbabilityManager.
 * @author anthony
 * @since 13/01/17.
 */
class ProbabilityManagerTest {

    private var fav_case_list : ArrayList<FavorableCase<String>> = ArrayList()

    @Before
    fun init_properties()
    {
        fav_case_list = ArrayList(200)
    }


    @Test(expected = Exception::class)
    fun favorable_case_list_is_empty_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list)
    }

    @Test(expected = Exception::class)
    fun favorable_case_size_is_out_of_range_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list)
    }

    @Test(expected = Exception::class)
    fun potential_case_is_negative_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list, -1)
    }
    @Test(expected = Exception::class)
    fun potential_case_is_null_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list, 0)
    }
    @Test(expected = Exception::class)
    fun potential_case_equals_1_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list, 1)
    }

    @After
    fun free_properties()
    {
        fav_case_list.clear()
    }
}