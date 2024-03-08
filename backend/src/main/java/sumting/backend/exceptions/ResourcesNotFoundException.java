package sumting.backend.exceptions;

public class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(String message) {
        super(message);
    }
}
