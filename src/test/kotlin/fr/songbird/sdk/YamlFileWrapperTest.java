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

import fr.songbird.sdk.stringparser.YamlFileWrapper;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * <h2>Fonctionnement
 * <p>
 * La classe YamlFileWrapper encapsulera une instance de la classe Yaml pour
 * offrir des routines assurant l'intégrité du fichier avant son chargement dans
 * la RAM.
 * <p>
 * <strong>Note:</strong> L'instance passée en paramètre doit forcément être
 * vierge. Dans tous les cas, les opérations effectuées sur cette dernière
 * seront écrasées.
 * <h2>Utilisation
 * <p>
 * YamlFileWrapper propose plusieurs services:
 * <ul>
 * <li>filePresenceChecking();</li>
 * <li>fileIntegrityChecking();</li>
 * <li>writeThisFile();</li>
 * <li>loadHim();</li>
 * </ul>
 *
 * @author songbird
 * @since 13 dec., 2016
 */
public class YamlFileWrapperTest {

    /**
     * <p>
     * Répertoire courant du projet en local.
     * <p>
     * Répertoire courant de l'archive (.jar) lorsqu'elle sera déployée.
     */
    private final File CURRENT_DIRECTORY = new File(System.getProperty("user.dir"));
    /**
     * <p>
     * En l'occurrence, j'ai choisi de générer le fichier dans le répertoire
     * source dédié aux tests pour éviter de l'écrire n'importe où.
     * <p>
     * En pratique, le wrapper devra écrire son fichier de configuration à un
     * endroit bien précis.
     */
    private final Path TARGET_PATH = Paths.get(CURRENT_DIRECTORY.toString(), "src", "test", "java", "target_file.yaml");

    private final File ABSTRACT_FILE = new File(TARGET_PATH.toString());

    private final Map<String, Object> DEFAULT_SKELETON;
    {
        System.out.println("default skeleton loading...");
        DEFAULT_SKELETON = new HashMap<>();
        ArrayList<ArrayList<Integer>> container = new ArrayList<>(2);
        final Map<String, ArrayList<Integer>> teams_coord = new HashMap<>();
        teams_coord.put("OR", new ArrayList<>(3));
        teams_coord.put("WHITE", new ArrayList<>(3));
        // *
        // /\
        // [*][*]
        // /|\/|\
        // [xyz][xyz]
        DEFAULT_SKELETON.put("initial_spawn_coord", teams_coord);

        // *
        // /\
        // [*][*]
        // /|\/|\
        // [xyz][xyz]
        DEFAULT_SKELETON.put("bed_spawn_coord", container.clone());
        // on clone, sinon c'est l'invalidation assurée

        ArrayList<ArrayList<Integer>> fat_container = new ArrayList<>(8);

        // *
        // ////\\\\
        // **** ****
        // \||/ \||/
        // [xyz]x4 [xyz]x4

        DEFAULT_SKELETON.put("villager_spawn_coord", fat_container);
        // *
        // ////\\\\
        // **** ****
        // \||/ \||/
        // [xyz]x4 [xyz]x4

        DEFAULT_SKELETON.put("dropper_spawn_coord", fat_container.clone());
        // on clone, sinon c'est l'invalidation assurée

//		Le nom du monde à charger
        DEFAULT_SKELETON.put("world_name", "whatever you like");
//		Le nombre minimum de joueurs dans une équipe
        DEFAULT_SKELETON.put("player_number", 1);
//      Le nombre de skylies gagnés pour avoir terminé la partie
        DEFAULT_SKELETON.put("skylies_amount_party", 0);
//		Le nombre de skylies gagnés pour avoir éliminé un adversaire
        DEFAULT_SKELETON.put("skylies_amount_on_kill", 0);
//		Le nombre de skylies gagnés pour avoir aidé un allié à éliminer un adversaire
        DEFAULT_SKELETON.put("skylies_amount_on_assistance", 0);

        System.out.println("default skeleton loading complete.\nDefault skeleton: " + DEFAULT_SKELETON);
    }
    private final YamlFileWrapper WRAPPER_OVERLOADED = new YamlFileWrapper(new Yaml(), ABSTRACT_FILE, DEFAULT_SKELETON);


    @Test
    /**
     * <p>
     * Dans ce test, on part du principe que le fichier n'existe pas.
     */
    public void YamlFileWrapper_filePresenceChecking() {

		/* déclarations */

        final boolean isHere = WRAPPER_OVERLOADED.filePresenceChecking();

		/* log */

        System.out.println("TARGET_PATH == " + TARGET_PATH);

		/* assertions */

        assertThat("Le fichier existe, ce n'est pas normal.\n" + "Elements de réponse:\n"
                + "Le fichier pourrait bien exister, à ce moment-là réferrez-vous à la variable TARGET_PATH et supprimez-le\n"
                + "Le fichier n'existe vraiment pas et alors la méthode a un souci.", isHere, is(false));
    }

    @Test
    /**
     * <p>
     * Dans ce test, on part du principe que le fichier existe et qu'il doit
     * subir des tests pour affirmer ou infirmer son intégrité.
     * <p>
     * Puisque, de base, le fichier ne doit jamais survivre au-de-là d'un test,
     * nous allons le créer en début de méthode.
     */
    public void YamlFileWrapper_fileIntegrityChecking() {
        // On procède à sa création, on catch.
        try {
            ABSTRACT_FILE.createNewFile();
            // On lance la routine pour savoir si le contenu du fichier répond
            // à nos attentes.
            final boolean ITSOK = WRAPPER_OVERLOADED.fileIntegrityChecking();
            assertThat(ITSOK, is(true));
        } catch (IOException ioe0) {
            ioe0.printStackTrace();
        } finally {
            // On supprime le fichier qui ne doit pas survivre au test.
            ABSTRACT_FILE.delete();
        }
    }

    @Test
    /**
     * <p>
     * Ce test consiste à écrire un fichier de configuration vierge au cas où il
     * ne serait pas intègre.
     * <p>
     * Une HashMap sera spécialement créée pour représenter le squelette par
     * défaut du fichier de configuration. Une instance de la classe
     * YamlFileWrapper sera également créée pour utiliser la surcharge du
     * constructeur.
     */
    public void YamlFileWrapper_writeThisFile() {
        final YamlFileWrapper YFW = new YamlFileWrapper(new Yaml(), ABSTRACT_FILE, DEFAULT_SKELETON);
        try {
            ABSTRACT_FILE.createNewFile();
            assert (YFW.filePresenceChecking()) : "Le fichier n'existe pas.";
            long file_length = ABSTRACT_FILE.length();
            System.out.println("file_length == " + file_length);
            YFW.writeThisFile();
            long file_length_ = ABSTRACT_FILE.length();
            System.out.println("file_length_ == " + file_length_);
            assert (file_length_ > file_length) : "Le fichier n'a pas subi de modifications.\nDebug:"
                    + "\nfile_length == " + file_length + "\nfile_length_ == " + file_length_;
        } catch (IOException ioe0) {
            ioe0.printStackTrace();
        } finally {
            ABSTRACT_FILE.delete();
        }
    }

    @Test
    public void YamlFileWrapper_loadHim() {
        final Map<String, Object> fake_map = new HashMap<>();
        fake_map.put("yolol", null);
        fake_map.put("gamer_number", null);
        fake_map.put("droper_number", null);
        fake_map.put("team_color", null);
        final YamlFileWrapper fake_wrapper = new YamlFileWrapper(new Yaml(), ABSTRACT_FILE, fake_map);
        fake_wrapper.writeThisFile();
        assert (WRAPPER_OVERLOADED.filePresenceChecking()) : "Le fichier n'existe pas.";
        assert (WRAPPER_OVERLOADED
                .fileIntegrityChecking()) : "Le fichier ne peut être lu et/ou modifié. Il peut être corrompu.";
        final boolean load_success = WRAPPER_OVERLOADED.loadHim();

        ABSTRACT_FILE.delete();
        // Le fichier ne respecte effectivement pas la structure par défaut
        // la méthode abandonne donc le chargement.
        assertThat("Le fichier ne doit pas respecter le squelette par défaut, revoyez votre test.", load_success,
                is(false));
    }

    @Test
    public void YamlFileWrapper_getMap() {
        WRAPPER_OVERLOADED.writeThisFile();
        WRAPPER_OVERLOADED.loadHim();
        Map<String, Object> current_skeleton = null;
        try {
            current_skeleton = WRAPPER_OVERLOADED.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("current_skeleton == " + current_skeleton);
        ABSTRACT_FILE.delete();
        assertThat("Le binding est nul, vérifiez les logs.", current_skeleton, is(notNullValue()));
    }
}
