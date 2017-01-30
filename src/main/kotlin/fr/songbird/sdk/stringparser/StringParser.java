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

package fr.songbird.sdk.stringparser;

import fr.songbird.sdk.stringparser.listener.StringParserListener;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Système générique d'analyses sur pattern.
 * Cette classe peut être utilisée lorsque vous avez plus d'une
 * dizaines de pattern dont il faut tester l'égalité avec d'autres entrées.
 * Pour fonctionner correctement, la classe devra s'appuyer sur une base de données,
 * peu importe le format (fichier texte basique, un tableau, fichier json, fichier yml...) et renverra l'état du test
 * une fois terminé.
 * StringParser est prévu, à terme, pour être un service embarqué et léger. De ce fait
 * la classe n'aura jamais recours à des serveurs de base de données classiques.
 * @author anthony
 * @since 24/01/17.
 */
public final class StringParser {

    /**
     * Le type de fichier utilisé en tant que base de données.
     * Note: Cet attribut est assigné à {@code null} si l'utilisateur
     * ne sollicite pas de fichier.
     */
    private FileType fileType = null;
    /**
     * Cet attribut récupérera une référence vers la
     * ressource qui servira de base de données
     * aux services de la classe.
     * <p>
     * Concernant le choix d'avoir un seul attribut typé Object plutôt que d'avoir deux attributs distincts
     * (l'un de type List, l'autre de type File), j'ai choisi de les réunir dans un seul et même attribut
     * car ce service, étant très générique, n'a pas vocation de traiter d'autres types de données que ceux-ci.
     * (des vecteurs et des fichiers, en l'occurrence)
     * Dans la procédure submit_pattern, le type de cet attribut est testé car la procédure peut être surchargée et
     * potentiellement contenir des bugs si l'utilisateur venait à initialiser une instance de la classe
     * StringParser avec une List puis ensuite utiliser submit_pattern comme si l'instance était initialisée avec un
     * chemin vers un fichier à solliciter. (ce comportement serait illégal)
     */
    private Object light_database = null;
    /**
     * La liste des instances inscrites en tant qu'écouteur
     * des événements relatifs aux services de la classe.
     */
    private List<StringParserListener> listeners_list = new ArrayList<>();


    /**
     * Ce constructeur vous permet de passer en paramètre
     * un chemin vers votre base de données.
     * @param path_to_database Le chemin vers votre base de données.
     *                         Note: le chemin doit forcément pointer vers un fichier
     *                         sinon une exception sera levée.
     * @param fileType Le format de votre base de données.
     *                 Notez que si le format renseigné n'est pas le même que celui trouvé
     *                 par le programme, une exception sera levée.
     */
    public StringParser(Path path_to_database, FileType fileType)
    {
        if(path_to_database == null)
            throw new RuntimeException("Le chemin passé en paramètre est nul.");
        if(path_to_database.toString().isEmpty())
            throw new RuntimeException("Le chemin passé en paramètre est vide, il ne pointe sur rien.");
        if(fileType == null)
            throw new RuntimeException("Le type de fichier passé en paramètre est nul.");
        light_database = path_to_database.toFile();
        this.fileType = fileType;
    }

    /**
     * Il est possible de passer une liste de patterns à
     * analyser grâce à ce constructeur.
     * La liste sera sollicitée comme un fichier peut l'être.
     * Si vous souhaitez stocker un grand nombre de données, privilégiez la surcharge
     * {@link StringParser#StringParser(Path, FileType)}.
     * @param virtual_database La liste qui sera utilisée par les services de la classe
     *                         pour comparer les entrées soumises dans un second temps.
     * @see StringParser#submit_pattern(String)
     */
    public StringParser(List<String> virtual_database)
    {
        if(virtual_database == null)
            throw new RuntimeException("La référence passée en paramètre est nulle.");
        if(virtual_database.isEmpty())
            throw new RuntimeException("La liste passée en paramètre est vide.");
        light_database = virtual_database;
    }

    /**
     * Appelle la méthode {@link fr.songbird.sdk.stringparser.listener.EqualityStringListener#whenInputIsFound(String, String) EqualityStringListener.whenInputIsFound(String, String)}
     * sur tous les listeners qui l'ont implémentée.
     * @param targeted_input La chaîne de caractères qui a été passée en paramètre à la méthode {@link StringParser#submit_pattern(String, boolean)}.
     * @param input_found La chaîne de caractères qui a été jugée "égale" à celle soumise.
     */
    private void fireEqualityStringEvent(final String targeted_input, final String input_found)
    {
        for(StringParserListener stringParserListener: listeners_list)
        {
            stringParserListener.whenInputIsFound(targeted_input, input_found);
        }
    }

    private void fireInequalityStringEvent(final String targeted_input)
    {
        for(StringParserListener stringParserListener: listeners_list)
        {
            stringParserListener.whenInputIsNotFound(targeted_input);
        }
    }

    /**
     * Permet de déterminer si l'instance descendant de la classe {@link Object} est bien
     * une instance de la classe {@link File}.
     * @param light_database La base de données représentée par un fichier. (dont le format n'est pas déterminé)
     * @return Une version castée en {@link File}.
     * @throws RuntimeException Si la référence passée en paramètre n'est pas une instance
     * de la classe File, le service indiquera une erreur. Cette méthode n'étant utilisée que
     * dans un seul cas, le paramètre passé doit <strong>forcément</strong> être de type File.
     * Si l'erreur survient lors de l'appel de cette méthode, vous l'utilisez très certainement dans un autre contexte
     * que celui pour lequel elle a été créée.
     */
    private File cast_light_database_to_file(Object light_database) {
        return (File)light_database;
    }

    /**
     * Vérifie si l'extension du fichier sur lequel le programme va
     * opérer est identique à celle du type de fichier choisi précédemment par l'utilisateur.
     * @param file_database Le fichier servant de base de données.
     * @param fileType Une instance de l'enum {@link FileType}.
     * @throws RuntimeException Ne pouvant garantir le bon fonctionnement.
     */
    private void check_extension_file(final File file_database, final FileType fileType) {
        final String must_have_extension = fileType.get_extension_file();
        if(!file_database.getName().endsWith(must_have_extension))
            throw new RuntimeException("Le fichier " + file_database.getName() + " est censé disposer de l'extension " + must_have_extension + ".");
    }

    /**
     * Parse et lit dans un fichier json pour fire les événements
     * relatifs à l'égalité entre les deux patterns.
     * @param file_database Le fichier servant de base de données.
     * @param pattern_to_string La chaîne de caractères passée en paramètre à la méthode {@link StringParser#submit_pattern(String, boolean)}.
     */
    private void read_json_file(File file_database, String pattern_to_string) {

    }

    /**
     * Parse et lit dans un fichier yml pour fire les événements
     * relatifs à l'égalité entre les deux patterns.
     * @param file_database Le fichier servant de base de données.
     * @param pattern_to_string La chaîne de caractères passée en paramètre à la méthode {@link StringParser#submit_pattern(String, boolean)}.
     */
    private void read_yaml_file(File file_database, String pattern_to_string) {

    }

    /**
     * Lit simplement dans un fichier texte pour fire les événements
     * relatifs à l'égalité entre les deux patterns.
     * @param file_database Le fichier servant de base de données.
     * @param pattern_to_string La chaîne de caractères passée en paramètre à la méthode {@link StringParser#submit_pattern(String, boolean)}.
     */
    private void read_vanilla_file(File file_database, String pattern_to_string) {
        if(!file_database.exists())
            throw new RuntimeException("Le fichier pointé par le chemin suivant \"" + file_database.getAbsolutePath() + "\" n'existe pas. " +
                    "Créez ce fichier pour faire disparaître cette erreur, ou corrigez votre chemin.");
        if(file_database.length() == 0L)
            throw new RuntimeException("Votre fichier est vide. Veuillez le compléter pour faire disparaître cette erreur.");
        boolean found_at_least_once = false;
        try(BufferedReader buffer = new BufferedReader(new FileReader(file_database)))
        {
            for(String current_line; (current_line = buffer.readLine()) != null;)
            {
                if(current_line.equalsIgnoreCase(pattern_to_string)) {
                    fireEqualityStringEvent(pattern_to_string, current_line);
                    found_at_least_once = true;
                }
            }
            /* Si le pattern n'a pas matché
            * au moins une fois, on fire l'event opposé.*/
            if(!found_at_least_once)
                fireInequalityStringEvent(pattern_to_string);
        } catch(IOException ioe_exception)
        {
            /*
            * Si une erreur, autre qu'une FNFE, survient,
            * on print la stacktrace.*/
            ioe_exception.printStackTrace();
        }
    }

    /**
     * Permet de passer en paramètre une entrée qui
     * sera soumise à analyse.
     * @param pattern_to_string Le pattern qui sera soumis à analyse.
     * @param file_reading A initialiser à {@code true} si la base de données que vous utilisez
     *                     est un fichier, {@code false} si c'est autre chose. (e.g. une collection)
     */
    public final void submit_pattern(String pattern_to_string, boolean file_reading)
    {
        if(file_reading)
        {
            if(fileType == null)
                throw new RuntimeException("Vous avez modifié l'état du flag file_reading à true, alors que vous " +
                        "n'avez pas utilisé le constructeur adéquat pour utiliser un fichier comme base de données.\n" +
                        "Le processus va être abandonné.");
            final File file_database = cast_light_database_to_file(light_database);
            switch(fileType)
            {
                case VANILLA:{
                    check_extension_file(file_database, FileType.VANILLA);
                    read_vanilla_file(file_database, pattern_to_string);
                }break;
                case JSON:{
                    check_extension_file(file_database, FileType.JSON);
                    read_json_file(file_database, pattern_to_string);
                } break;
                case YAML:{
                    check_extension_file(file_database, FileType.YAML);
                    read_yaml_file(file_database, pattern_to_string);
                } break;
            }

        }
        else
        {
            if(fileType != null)
                throw new RuntimeException("Le type de fichier n'est pas nul, vous avez donc utilisé un constructeur " +
                        "inadapté à ce que vous souhaitez faire. Merci de vous référer à la documentation de la bibliothèque.");

        }
    }


    /**
     * Surcharge de la méthode {@link StringParser#submit_pattern(String, boolean)}.
     * En utilisant cette surcharge, le programme assume que vous ne souhaitez pas utiliser
     * de fichier pour stocker vos données.
     * @param pattern_to_string Le pattern qui sera soumis à analyse.
     */
    public final void submit_pattern(String pattern_to_string)
    {
        submit_pattern(pattern_to_string, false);
    }

    /**
     * Inscrit une instance d'une classe implémentant l'interface {@link StringParserListener}
     * à la liste des écouteurs d'événements.
     * @param stringParserListener L'instance implémentant l'interface {@link StringParserListener}.
     * @see fr.songbird.sdk.stringparser.listener.EqualityStringListener EqualityStringListener
     * @see fr.songbird.sdk.stringparser.listener.InequalityStringListener InequalityStringListener
     */
    public final void addListener(StringParserListener stringParserListener)
    {
        listeners_list.add(stringParserListener);
    }

}
