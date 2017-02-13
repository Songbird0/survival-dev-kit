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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static fr.songbird.sdk.probabuilder.FavorableCase.as_fav_case_list;

/**
 * Created by anthony on 21/01/17.
 */
public class FavorableCaseTest {


    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test(expected = Exception.class)
    public void negative_favorable_case_percentage() throws Exception {
            new FavorableCase<String>("my awesome item", "item content", -1);
    }

    @Test(expected = Exception.class)
    public void null_favorable_case_percentage() throws Exception {
            new FavorableCase<String>("my awesome item", "item content", 0);
    }

    @Test
    public void get_favorable_case_to_int_equals_10() throws Exception {
        //potential_case vaut 100
        //partons sur une échelle simple
        final FavorableCase<String> f = new FavorableCase<>("my awesome item", "item content", 10);
        final Integer favorable_case_to_int = f.get_favorable_case_to_int(100);
        final Integer favorable_case_to_int_result = 10;
        assert(favorable_case_to_int.equals(favorable_case_to_int_result)) : "Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int";
    }
    @Test
    public void get_favorable_case_to_int_equals_50() throws Exception {

        //potential_case vaut 100
        //partons sur une échelle simple

        final FavorableCase<String> f = new FavorableCase<>("my awesome item", "item content", 50);
        final Integer favorable_case_to_int = f.get_favorable_case_to_int(100);
        final Integer favorable_case_to_int_result = 50;
        assert(favorable_case_to_int.equals(favorable_case_to_int_result)) : "Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int";
    }
    @Test
    public void get_favorable_case_to_int_equals_75() throws Exception {
        //potential_case vaut 100
        //partons sur une échelle simple
        final FavorableCase<String> f = new FavorableCase<>("my awesome item", "item content", 75);
        final Integer favorable_case_to_int = f.get_favorable_case_to_int(100);
        final Integer favorable_case_to_int_result = 75;
        assert(favorable_case_to_int.equals(favorable_case_to_int_result)) : "Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int";

    }
    @Test
    public void get_favorable_case_to_int_equals_100()
    {
        try
        {
            //potential_case vaut 200
            //partons sur une échelle simple
            final FavorableCase<String> f = new FavorableCase<>("my awesome item", "item content", 50);
            final Integer favorable_case_to_int = f.get_favorable_case_to_int(200);
            final Integer favorable_case_to_int_result = 100;
            assert(favorable_case_to_int.equals(favorable_case_to_int_result)) : "Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int";

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Test(expected = Exception.class)
    public void get_favorable_case_to_int_equals_potential_case() throws Exception {
        //potential_case vaut 100
        //partons sur une échelle simple
        //on attend ici une erreur
        //Le nombre de cas favorables pour un item ne doit pas être égal
        //au nombre de cas potentiels, puisque le tirage au sort
        //aura une issue certaine.

        final FavorableCase<String> f = new FavorableCase<>("my awesome item", "item content", 100);
        final Integer favorable_case_to_int = f.get_favorable_case_to_int(100);
    }

    @Test
    public void cloned_object_test() throws Exception {

        final FavorableCase<String> foo = new FavorableCase<>("my awesome item", "item content", 10);
        final FavorableCase<String> bar= foo.copy();
        assert(foo != bar): "bar est une référence vers foo, ce n'est pas normal.";
    }

    @Test
    public void object_equality_test() throws Exception {

        final FavorableCase<String> foo = new FavorableCase<>("my awesome item", "item content", 10);
        final FavorableCase<String> bar = foo.copy();
        assert(foo.equals(bar)) : "bar ne contient pas la même chose que foo.";
    }

    @Test
    public void as_fav_case_list_size_returns_2()
    {
        final List<FavorableCase<String>> favorableCases = as_fav_case_list("foo", 20, "bar");
        assert(favorableCases.size() == 2);
    }
    @Test
    public void as_fav_case_list_size_returns_4()
    {
        final List<FavorableCase<String>> favorableCases = as_fav_case_list("foo", 20, "bar", "baz", "bang");
        assert(favorableCases.size() == 4);
    }
    @Test
    public void as_fav_case_list_size_returns_6()
    {
        final List<FavorableCase<String>> favorableCases = as_fav_case_list("foo", 20, "bar", "baz", "bang", "qux", "quxx");
        assert(favorableCases.size() == 6);
    }
    @Test
    public void as_fav_case_list_size_returns_15()
    {
        final List<FavorableCase<String>> favorableCases = as_fav_case_list("foo", 20, "bar", "baz", "bang", "qux", "quxx", "quxxx", "quxxxx", "quxxxxx", "quxxxxxx", "quxx", "quuu", "kkk", "gg", "ppp");
        assert(favorableCases.size() == 15);
    }
    @Test
    public void as_fav_case_list_your_first_object_is_null()
    {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Votre premier objet est nul.");
        final List<FavorableCase<String>> favorableCases = as_fav_case_list(null, 20, "bar");
    }
    @Test
    public void as_fav_case_list_others_object_is_null()
    {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("La liste optionnelle d'objets supplémentaires est nulle.");
        final List<FavorableCase<String>> favorableCases = as_fav_case_list("foo", 20, null);
    }
}
