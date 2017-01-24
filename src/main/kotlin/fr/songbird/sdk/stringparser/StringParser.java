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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Système générique d'analyses sur pattern.
 * Cette classe peut être utilisée lorsque vous avez plus d'une
 * dizaines de pattern dont il faut tester l'égalité avec d'autres entrées.
 * Pour fonctionner correctement, la classe devra s'appuyer sur une base de données,
 * peu importe le format (fichier basique, un tableau, fichier json, fichier yml...) et renverra l'état du test
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
     * ne sollicite pas de fichiers.
     */
    private FileType fileType = null;
    /**
     * Cet attribut récupérera une référence vers la
     * ressource qui servira de base de données
     * aux services de la classe.
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
     * @param fileType Le format de votre base de données.
     *                 Notez que si le format renseigné n'est pas le même que celui trouvé
     *                 par le programme, une exception sera levée.
     */
    public StringParser(Path path_to_database, FileType fileType)
    {
        if(path_to_database == null)
            throw new RuntimeException("Le chemin passé en paramètre est nul.");
        if(fileType == null)
            throw new RuntimeException("Le type de fichier passé en paramètre est nul.");
        light_database = path_to_database.toFile();
        this.fileType = fileType;
    }

    /**
     * Il est possible de passer une liste de pattern à
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

    private void fireEqualityStringEvent(final String targeted_input, final String input_found)
    {
        for(StringParserListener stringParserListener: listeners_list)
        {
            stringParserListener.whenInputIsFound(targeted_input, input_found);
        }
    }

    /**
     * Permet de passer en paramètre une entrée qui
     * sera soumis à analyse.
     * @param pattern_to_string Le pattern qui sera soumis à analyse.
     * @param file_reading A initialiser à {@code true} si la base de données que vous utilisez
     *                     est un fichier, {@code false} si c'est autre chose. (e.g. une collection)
     */
    public final void submit_pattern(String pattern_to_string, boolean file_reading)
    {
        
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
    
    public final void addListener(StringParserListener stringParserListener)
    {
        listeners_list.add(stringParserListener);
    }

}
