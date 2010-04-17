package serializr.typesystem;

/**
 *
 */
public interface Field {

    String getName();

    TypeRef getTypeRef();

    boolean isOptional();
}
