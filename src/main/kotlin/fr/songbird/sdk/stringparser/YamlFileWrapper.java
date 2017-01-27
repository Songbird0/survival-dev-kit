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

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author songbird
 * @since 13 déc., 2016
 */
public class YamlFileWrapper {

    /**
     * Le fichier une fois chargé en mémoire.
     */
    private Yaml virtual_file = null;
    /**
     * Le chemin du fichier.
     */
    private File target_file = null;
    /**
     * La structure par défaut du fichier.
     */
    private Map<String, Object> default_skeleton = null;
    /**
     * Cet attribut est le squelette chargé en mémoire une fois le test
     * d'intégrité passé.
     */
    private Map<String, Object> current_skeleton = null;

    /**
     * L'unique constructeur de cette classe.
     * <p>
     * Il permet d'initialiser correctement les attributs sollicités par les
     * services fournis. Notez toutefois que si l'instance de la classe Yaml
     * passée en paramètre n'est pas vierge, elle sera écrasée.
     *
     * @param yaml L'instance vierge de la classe Yaml censée représenter le
     *             fichier dans la mémoire vive.
     * @param file Le chemin du fichier.
     * @param map  La structure de base dont doit disposer votre fichier quoi
     *             qu'il arrive.
     */
    public YamlFileWrapper(Yaml yaml, File file, Map<String, Object> map) {
        assert (yaml != null && file != null && map != null) : "L'un des binding passés en paramètre est nul.\nDebug:"
                + "\nyaml == " + yaml + "\nfile == " + file + "\nmap == " + map;
        virtual_file = yaml;
        target_file = file;
        default_skeleton = map;
    }

    /**
     * La méthode {@code filePresenceChecking()} est une routine permettant,
     * comme son nom l'indique, de vérifier si le fichier qui doit être chargé
     * est présent sur le disque de l'utilisateur.
     *
     * @return {@code true} dans le cas où le fichier existe, sinon
     * {@code false}.
     */
    public boolean filePresenceChecking() {
        target_file.getParentFile().mkdirs();
        if (target_file.exists())
            return true;
        return false;
    }

    /**
     * <h3>Règles</h3>
     * <ul>
     * <li>Le fichier doit pouvoir être lu.</li>
     * <li>Le fichier doit pouvoir être modifié.</li>
     * </ul>
     *
     * @return {@code true} si le fichier respecte les règles précédemment
     * énoncées, sinon {@code false}.
     */
    public boolean fileIntegrityChecking() {
        if (target_file.canRead() && target_file.canWrite()) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Cette méthode créé et écrit un fichier de configuration disposant du
     * squelette par défaut.
     * <p>
     */
    public void writeThisFile() {

        final String yaml_file_content = virtual_file.dump(default_skeleton);
        try {
            FileWriter writer = new FileWriter(target_file);
            writer.write(yaml_file_content);
            writer.close();
        } catch (IOException ioe0) {
            ioe0.printStackTrace();
        }
    }

    /**
     * Cette dernière méthode vient compléter ce que les autres ne fournissent
     * pas: une analyse.
     * <p>
     * La méthode {@code loadHim()} va, en chargeant le fichier en question, vérifier,
     * au minimum, si le squelette par défaut est respecté. Que la valeur des
     * clés soit nulle, ou non, importe peu, mais elles doivent au moins être
     * présentes.
     * <strong>Note</strong>: Ce service n'assure en aucun cas l'intégrité des valeurs
     * de chaque clés. Cette phase de test est à votre charge.
     *
     * @return {@code true} si le chargement s'est bien passé, sinon {@code false}.
     */
    public boolean loadHim() {
        try {
            // It's ok javac, good boy ! Go to sleep, now.
            @SuppressWarnings(value = "unchecked")
            final Map<String, Object> skeleton = (Map<String, Object>) virtual_file
                    .load(new FileInputStream(target_file));
            assert (skeleton != null) : "Le fichier qui devait être parsé est vide, vérifiez votre chemin.";
            // On récupère un set de clés pour chaque squelette
            final Set<String> key_set_default_skeleton = default_skeleton.keySet();
            final Set<String> key_set_skeleton = skeleton.keySet();
            // On récupère chaque itérateur pour commencer à les parcourir.
            final Iterator<String> default_skeleton_keys = key_set_default_skeleton.iterator();
            final Iterator<String> skeleton_keys = key_set_skeleton.iterator();
            // On parcours nos itérateurs.
            while (default_skeleton_keys.hasNext()) {
                // Si le programme tombe sur ne serait-ce qu'une seule clé
                // différente du squelette de base
                // tout est interrompu
                if (!(default_skeleton_keys.next().equals(skeleton_keys.next()))) {
                    return false;
                }
            }
            // Sinon, on load comme prévu notre squelette pour commencer à
            // effectuer
            // nos opérations.
            current_skeleton = skeleton;
        } catch (FileNotFoundException fnfe0) {
            fnfe0.printStackTrace();
        }
        return true;
    }

    /**
     * Cette méthode se charge de vous fournir la structure chargée dans la RAM
     * sous forme de {@link java.util.HashMap HashMap}.
     *
     * @return La structure du fichier de configuration chargé.
     * @throws Exception Notez toutefois que cette méthode lèvera une exception si
     *                   vous n'avez chargé aucun fichier.
     */
    public Map<String, Object> getMap() throws Exception {
        if (current_skeleton == null) {
            throw new Exception("Aucun fichier n'a été chargé.\nDebug:\n" + "current_skeleton == " + current_skeleton);
        }
        return current_skeleton;
    }

}
