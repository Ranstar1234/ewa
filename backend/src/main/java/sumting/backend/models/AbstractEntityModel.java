package sumting.backend.models;

public abstract class AbstractEntityModel<E> implements EntityModel {
    private int key;

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public void setKey(int key) {
        this.key = key;
    }
}
