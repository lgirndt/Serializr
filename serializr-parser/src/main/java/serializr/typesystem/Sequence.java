package serializr.typesystem;

/**
 *
 */
public interface Sequence extends Type {

    Iterable<? extends Field> getFields();
}
