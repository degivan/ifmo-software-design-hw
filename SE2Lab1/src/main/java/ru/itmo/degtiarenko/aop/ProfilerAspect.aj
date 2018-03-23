package ru.itmo.degtiarenko.aop;

import org.aspectj.lang.Signature;

import java.lang.reflect.Modifier;

public aspect ProfilerAspect {
    Object around(): execution(* *.*(..)) && !within(ProfilerAspect) && !within(Profiler) {
        long begin = System.nanoTime();
        Object result = proceed();
        long finish = System.nanoTime();

        Signature signature = thisJoinPointStaticPart.getSignature();
        if (!Modifier.isAbstract(signature.getDeclaringType().getModifiers())) {
            String method = signature.getName();
            Profiler.getInstance().methodCalled(method, finish - begin);
        }

        return result;
    }
}