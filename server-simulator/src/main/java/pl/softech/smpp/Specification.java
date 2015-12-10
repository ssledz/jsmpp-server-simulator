package pl.softech.smpp;

@FunctionalInterface
public interface Specification<T> {

    boolean isSatisfiedBy(T arg);

}
