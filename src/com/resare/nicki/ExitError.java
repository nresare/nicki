package com.resare.nicki;

/**
 * An exit that is fatal to the continuing operation of the service. Once this is thrown to
 * the top level, exit the application gracefully.
 */
public class ExitError extends Error {

}
