package org.example.test.data;

import java.util.function.Function;

public interface Generator <I,T>  {

    default T generate(){
        return generate(x->x);
    }

    default T generate(Function<I,I> inputModifier){
        return save(generateInput(inputModifier));
    }

    T save(I input);

    I generateInput();

    default I generateInput(Function<I,I> inputModifier){
        return inputModifier.apply(generateInput());
    }
}
