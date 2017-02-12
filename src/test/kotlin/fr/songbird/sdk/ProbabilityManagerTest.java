/*
 *    SurvivalDevKit, descendante de la bibliothèque utilitaire TheBareMinimum, mais en moins crade. :)
 *     Copyright (C) 2017  Defranceschi Anthony
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     If you need more information, feel free to contact me at chaacygg[at]gmail[dot]com.
 */

package fr.songbird.sdk;

import fr.songbird.sdk.probabuilder.FavorableCase;
import fr.songbird.sdk.probabuilder.ProbabilityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by anthony on 21/01/17.
 */
public class ProbabilityManagerTest {


    class Foo
    {
        private final String first_name;
        private final String last_name;
        private final Integer age;

        Foo(String first_name, String last_name, Integer age)
        {
            this.first_name = first_name;
            this.last_name = last_name;
            this.age = age;
        }
        public void say_hello()
        {
            System.out.println("Hi, I'm " + first_name + " " + last_name + ", and I'm " + age + " years old.");
        }
    }

    private ArrayList<FavorableCase<String>> fav_case_list;

    @Before
    public void init()
    {
        fav_case_list = new ArrayList<FavorableCase<String>>(200);
    }

    @Test
    public void fire_random_item_test() throws Exception {
        /**
         * On initialise les ressources que l'ont souhaite
         * tirer au sort.
         */
        final Foo author = new Foo("Song", "Bird", 18);
        final Foo a_guy = new Foo("John", "Doe", 40);
        final Foo another_guy = new Foo("Novelastre", "faiseur d'Eternité", 20);
        /**
         * On créé un wrapper pour chacune d'entre-elles.
         * Notez d'ailleurs que, dans ce contexte, chacun des items aura
         * 10% de chances d'être tiré au sort.
         */
        final FavorableCase<Foo> author_wrapper = new FavorableCase<>("author_wrapper", author, 10);
        final FavorableCase<Foo> a_guy_wrapper = new FavorableCase<>("a_guy_wrapper", a_guy, 10);
        final FavorableCase<Foo> another_guy_wrapper = new FavorableCase<>("another_guy_wrapper", another_guy, 10);
        /**
         * On ajoute les wrappers dans une liste prête à être
         * passée en paramètre au gestionnaire de probabilités.
         */
        final ArrayList<FavorableCase<Foo>> members_list  = new ArrayList<>(3);
        members_list.add(author_wrapper);
        members_list.add(a_guy_wrapper);
        members_list.add(another_guy_wrapper);
        /**
         * Si le nombre de cas potentiels n'est pas
         * précisé par l'utilisateur, le gestionnaire assumera
         * que le calcul s'effectuera sur 100 cas.
         * Techniquement, chacun des wrappers initialisés précédemment devraient donc réserver
         * 10 cas respectivement.
         *
         * Note: Effectivement, si 3 wrappers réservent 10% respectivement, nous n'opérons plus sur
         * 100 cas potentiels, mais bien 30. Ce n'est pas un problème, le gestionnaire
         * fait abstraction de ce qui n'a pas été ajouté.
         */
        final ProbabilityManager<Foo> probability_manager = new ProbabilityManager<>(members_list);

        final FavorableCase<Foo> foo_item_fired_1 = probability_manager.fire_random_item();
        foo_item_fired_1.getItemRef().say_hello();
        final FavorableCase<Foo> foo_item_fired_2 = probability_manager.fire_random_item();
        foo_item_fired_2.getItemRef().say_hello();
        final FavorableCase<Foo> foo_item_fired_3 = probability_manager.fire_random_item();
        foo_item_fired_3.getItemRef().say_hello();

    }


    @Test(expected = Exception.class)
    public void favorable_case_list_is_empty_test() throws Exception {
        final ProbabilityManager<String> proba_manager = new ProbabilityManager<>(fav_case_list);
    }

    @Test(expected = Exception.class)
    public void favorable_case_size_is_out_of_range_test() throws Exception {
        final ProbabilityManager<String> proba_manager = new ProbabilityManager<>(fav_case_list);
    }

    @Test(expected = Exception.class)
    public void potential_case_is_negative_test() throws Exception {
        final ProbabilityManager<String> proba_manager = new ProbabilityManager<>(fav_case_list, -1);
    }
    @Test(expected = Exception.class)
    public void potential_case_is_null_test() throws Exception {
        final ProbabilityManager<String> proba_manager = new ProbabilityManager<>(fav_case_list, 0);
    }
    @Test(expected = Exception.class)
    public void potential_case_equals_1_test() throws Exception {
        final ProbabilityManager<String> proba_manager = new ProbabilityManager<>(fav_case_list, 1);
    }

    @After
    public void free_properties()
    {
        fav_case_list.clear();
    }
}
