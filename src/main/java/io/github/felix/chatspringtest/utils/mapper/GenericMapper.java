package io.github.felix.chatspringtest.utils.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Classe utilitaire pour mapper automatiquement les champs communs entre deux objets de classes différentes.
 * Elle utilise la réflexion pour copier les valeurs des champs ayant le même nom et un type compatible.
 */
public final class GenericMapper  {// 🔒 La classe est finale pour empêcher l’héritage

    /**
     * Constructeur privé pour empêcher l’instanciation de cette classe utilitaire.
     */
    private GenericMapper() {
        throw new UnsupportedOperationException("can't instantiate this class");
    }

    /**
     * Méthode générique qui copie les valeurs des champs communs d'un objet source vers un objet cible.
     *
     * @param source      L'objet source contenant les données à copier (ne doit pas être null)
     * @param targetClass La classe de l'objet cible
     * @param <S>         Type de l'objet source
     * @param <T>         Type de l'objet cible
     * @return Un nouvel objet de type `T` contenant les champs copiés de `source`
     */
    public static <S, T > T map(final S source, final Class<T> targetClass) {
        // Vérifier si l'objet source est null (on ne peut pas mapper un objet inexistant)
        if (source == null) return null;
        try {
            // Créer une nouvelle instance de l'objet cible
            final T target = targetClass.getDeclaredConstructor().newInstance();

            // Récupérer la liste des champs (variables) de la classe source et cible
            final Field[] sourceFields = source.getClass().getDeclaredFields();
            final Field[] targetFields = target.getClass().getDeclaredFields();

            // Parcourir tous les champs de la classe source
            for (final Field sourceField : sourceFields) {
                sourceField.setAccessible(true); // Permet d'accéder aux champs même s'ils sont privés

                // Parcourir les champs de la classe cible
                for (final Field targetField : targetFields) {
                    targetField.setAccessible(true);

                    // Vérifier si le champ existe dans les deux classes et que les types sont compatibles
                    if (sourceField.getName().equals(targetField.getName())) {
                        targetField.getType().isAssignableFrom(sourceField.getType());
                        targetField.set(target, sourceField.get(source));
                    }
                }
            }
            // Retourne l'objet cible avec les valeurs copiées
            return target;

        } catch (Exception e) {
            // En cas d'erreur (ex: classe sans constructeur par défaut), on lance une exception
            throw new RuntimeException("error while mapping " + source + " to " + targetClass + " " + e.getMessage(), e );
        }
    }

}
