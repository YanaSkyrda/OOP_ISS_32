package com.momotenko.lab1.model.entity.utils;

import com.momotenko.lab1.model.entity.clothes.*;
import com.momotenko.lab1.model.entity.weapons.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

/**
 * Class that implements ArgumentsProvider.
 * It's a helper class for annotation @ArgumentsSource
 * It is used for creating a parametrized test
 */
public class AmmunitionArgumentProvider implements ArgumentsProvider {
    /** Providing classes for parametrized test */
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Helmet.class,
                Chestplate.class,
                Gloves.class,
                Pants.class,
                Boots.class,
                Arrow.class,
                Bow.class,
                Shield.class,
                Spear.class,
                Sword.class).map(Arguments::of);
    }
}
