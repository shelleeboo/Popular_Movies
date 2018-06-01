package io.shellee.movies.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * This scope ensures that there is only one instance of the dependencies
 * created by Dagger.
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface MoviesApplicationScope {
}
