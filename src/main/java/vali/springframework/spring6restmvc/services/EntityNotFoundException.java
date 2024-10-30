package vali.springframework.spring6restmvc.services;

    public class EntityNotFoundException extends RuntimeException {

        public EntityNotFoundException(String message) {
            super(message);
        }
    }
