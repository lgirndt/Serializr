package serializr.typesystem;

/**
 *
 */
public interface TypeRef {
    Type getSerializrType();

    void applySerializrType(Type type);

    TypeMatch getTypeMatch();
}
