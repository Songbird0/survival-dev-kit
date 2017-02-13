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

package fr.songbird.sdk.probabuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Le gestionnaire de probabilités permet d'établir le nombre de cas
 * possibles maximum et fournir un service de tirage au sort pour récupérer l'instance
 * servie une fois la procédure terminée.
 *
 * <p>
 * <strong>Note</strong>: Comme expliqué dans la documentation dédié au paramètre type {@code T}, une instance
 * de la classe {@code ProbabilityManager} ne peut gérer qu'un seul type d'objets à la fois.
 * De ce fait, si vous créez une instance de la classe avec pour type {@code T} des objets de la classe {@link String}
 * le gestionnaire va différencier ces chaînes grâce à leur contenu.
 * Etant un système très générique, cette classe ne vous empêchera pas de le détourner: vous pouvez très bien soumettre
 * plusieurs fois la même chaîne, le service ne plantera pas, mais vous enverra quand même un warning si ce n'était pas voulu.
 *
 * @author songbird
 * @since 12 janv., 2017
 * @param <T> Le type des items que la classe devra traiter.
 * <strong>Note</strong>: Le type passé à la classe {@code ProbabilityManager} doit être identique à celui de la classe {@link FavorableCase},
 * sinon ça ne compilera pas.
 */
public final class ProbabilityManager<T> {
    /**
     * Le nombre de cas potentiels.
     */
    private int potential_case;
    /**
     * Liste des cas favorables.
     */
    private final ArrayList<FavorableCase<T>> fav_case;
    /**
     * Ce booléen permet de savoir si le nombre de cas favorables
     * est inférieur au nombre de cas potentiels.
     */
    private boolean favorable_case_sum_smaller_than_default_potential_case;
    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = Logger.getLogger(ProbabilityManager.class.getSimpleName());

    /**
     * Constructeur par défaut du système de probabilités.
     * <p>
     * Pour conserver une certaine souplesse, il n'y a pas de limite à respecter pour
     * le nombre de cas potentiels sur lequel se basera le système pour effectuer le
     * tirage au sort.
     * @param fav_case La liste des objets sur lequel on pourra tomber lors d'un tirage au sort.
     * @param potential_case Le nombre de cas potentiels maximum.
     *                       Notez toutefois que ce paramètre peut très bien être modifié à la volée
     *                       par le système si le maximum renseigné ne convient pas.
     *                       Vous pouvez utiliser la surcharge de ce constructeur si vous ne souhaitez
     *                       pas utiliser le constructeur par défaut.
     * @throws Exception Si le nombre de cas potentiels est nul.
     * Si le nombre de cas potentiels est négatif.
     * Si le nombre de cas potentiels est égal à 1.
     * @see ProbabilityManager#ProbabilityManager(ArrayList)
     */
    public ProbabilityManager(ArrayList<FavorableCase<T>> fav_case, int potential_case) throws Exception{
        assert(fav_case != null) :"fav_case_list est nul.";
        if(fav_case.isEmpty())
            throw new Exception("La liste est vide.");
        if(fav_case.size() > potential_case)
            throw new Exception("Les items censés représenter les cas favorables ne peuvent pas être supérieurs aux cas potentiels/possibles.\n"
                    + "Exemple:\n"
                    + "Un objet correspond à au moins 1 cas favorable, donc si il y a 150 objets alors qu'il y a 100 cas possibles, c'est illogique."
                    + "\nVous pouvez par contre mettre au moins autant d'objets dans le tableau que de cas possibles.");

        this.fav_case = fav_case;
        if(potential_case < 0)
            throw new Exception("Le nombre de cas potentiels est négatif.");
        if(potential_case == 0)
            throw new Exception("Le nombre de cas potentiels est nul.");
        if(potential_case == 1)
            throw new Exception("Le nombre de cas potentiels est égal à 1, la probabilité d'obtenir quelque chose est certaine.");
        this.potential_case = potential_case;
    }

    /**
     * Surcharge du constructeur par défaut.
     * @param fav_case La liste des objets sur lequel on pourra tomber lors d'un tirage au sort.
     * @throws Exception Si l'un des paramètres passés ne respecte pas les contrats du constructeur.
     * En l'occurrence, seul le paramètre {@code fav_case} pourrait déclencher une erreur.
     * @see ProbabilityManager#ProbabilityManager(ArrayList, int)
     */
    public ProbabilityManager(ArrayList<FavorableCase<T>> fav_case) throws Exception
    {
        this(fav_case, 100);
    }

    /**
     * Méthode "spécialement" créée pour l'implémentation dédiée à SCGames.
     * Cette méthode vous permet de libérer la mémoire utilisée par les services
     * de votre gestionnaire de probabilités.
     * Elle n'est à utiliser que lorsque vous ne vous servez plus de la ressource.
     * <p>
     * <strong>Assurez-vous de savoir ce que vous faites lors de son utilisation, car le service sera HS après son appel.</strong>
     * <p>
     * Exemple de cas d'utilisation:
     * Vous souhaitez créer un gestionnaire de probabilités se trouvant dans un try/catch (le compilateur vous obligera à catcher le système
     * de toute manière). Seulement, il se pourrait que le système plante. Vous pouvez donc utiliser la méthode
     * {@code free()} pour libérer une partie de la mémoire utilisée par le système:
     * <br><br>
     * <pre>
     *    {@literal ProbabilityManager<String>} proba_sys = null;
     *     try
     *     {
     *        {@literal proba_sys = new ProbabilityManager<>(...);}
     *     }catch(Exception e)
     *     {
     *         e.printStackTrace();
     *     }finally
     *     {
     *         proba_sys.free();
     *     }
     * </pre>
     */
    public void free()
    {
        this.fav_case.clear();
    }

    /**
     * Tire au sort un item contenu par la liste passée en paramètre
     * au constructeur.
     * @return L'instance d'un item wrappé par une instance
     * de la classe {@link FavorableCase} tirée au sort.
     */
    public FavorableCase<T> fire_random_item() {
        final ArrayList<FavorableCase<T>> items_list = init_items_list_content();
        final Random random = new Random();
        if(favorable_case_sum_smaller_than_default_potential_case)
        {
            /**
             * Si la somme de tous les cas favorables n'est pas égale au nombre de cas
             * potentiels prévus au départ, selon les conditions des tests imposées dans les autres
             * méthodes, on récupère la somme puis on établi de nouveau la limite avant le tirage au sort.
             */
            final int favorable_case_sum = get_favorable_case_sum();
            return items_list.get(random.nextInt(favorable_case_sum));
        }
        return items_list.get(random.nextInt(100));
    }

    /**
     * Calcule la somme des cas favorables en additionnant
     * les cas favorables de chaque item de la liste.
     * @return La somme des cas favorables.
     */
    private int get_favorable_case_sum() {
        Integer favorable_case_sum = 0;
        for(FavorableCase<T> favorableCase : fav_case)
            try {
                favorable_case_sum += favorableCase.get_favorable_case_to_int(potential_case);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return favorable_case_sum;
    }

    /**
     * Calcule et alloue l'espace dans une liste qui
     * contiendra tous les clones des items.
     * @return La liste contenant tous les clones des items, préparée
     * à être sollicitée par le système de probabilité.
     */
    private ArrayList<FavorableCase<T>> init_items_list_content() {
        final ArrayList<FavorableCase<T>> items_list = init_items_list_size();
        final int favorable_case_sum = get_favorable_case_sum();
        if(favorable_case_sum > potential_case)
            try {
                throw new Exception("Erreur sémantique: La somme des cas favorables est plus élevée que le nombre de cas potentiels." +
                        "\nSomme de tous les cas favorables est égal à "+favorable_case_sum+" alors qu'il y a " + potential_case + " cas potentiels.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(favorable_case_sum < potential_case) {
            LOGGER.log(Level.WARNING, "La somme des cas favorables n'est pas égal au nombre de cas potentiels, vous pouvez encore remplir votre liste." +
                    "\nSomme de tous les cas favorables est égal à "+favorable_case_sum+" alors qu'il y a "+potential_case+" cas potentiels.");
            favorable_case_sum_smaller_than_default_potential_case = true;
        }
        /*
         * Numérote les items pour les identifier
         * dans les logs.
         */
        Integer favorable_case_object_id = 0;
        for(FavorableCase<T> favorableCase : fav_case)
        {
            try
            {
                /*
                 * Numérote les itérations pour savoir
                 * combien de fois l'item a été
                 * cloné durant la phase de remplissage.
                 */
                Integer iterations = 0;
                LOGGER.log(Level.FINER, "Calcul du nombre de cas favorables pour l'item N°" +favorable_case_object_id+".");
                final Integer current_favorable_case_to_int = favorableCase.get_favorable_case_to_int(potential_case);
                while(iterations < current_favorable_case_to_int)
                {
                    items_list.add(favorableCase.copy());
                    iterations++;
                }
                LOGGER.log(Level.FINER, "N°" + favorable_case_object_id + " devait être cloné "+current_favorable_case_to_int+" fois et a été cloné "+iterations+" fois.");
                favorable_case_object_id++;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return items_list;
    }

    /**
     * Initialise la taille de la liste qui contiendra
     * les clones des items.
     * @return Une liste adaptée aux nombres de clones.
     */
    private ArrayList<FavorableCase<T>> init_items_list_size() {
        return new ArrayList<>(get_items_list_size());
    }

    /**
     * Renvoie la taille de la liste.
     * @return La taille de la liste.
     */
    private int get_items_list_size()
    {
        return this.potential_case;
    }
}
