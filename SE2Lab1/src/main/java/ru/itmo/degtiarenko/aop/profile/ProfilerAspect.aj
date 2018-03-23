package ru.itmo.degtiarenko.aop.profile;

import org.aspectj.lang.Signature;

import java.lang.reflect.Modifier;

public aspect ProfilerAspect {
    Object around(): execution(* *(..)) && !within(ru.itmo.degtiarenko.aop.profile.ProfilerAspect) && !within(ru.itmo.degtiarenko.aop.profile.Profiler) {
        long begin = System.nanoTime();
        Object result = proceed();
        long finish = System.nanoTime();

        Signature signature = thisJoinPointStaticPart.getSignature();
        String canonName = signature.getDeclaringType().getCanonicalName();

        if (!Modifier.isAbstract(signature.getDeclaringType().getModifiers()) &&
                canonName.contains(Profiler.getPackageName())) {
            String method = signature.getName();
            Profiler.getInstance().methodCalled(method, finish - begin);
        }

        return result;
    }
}