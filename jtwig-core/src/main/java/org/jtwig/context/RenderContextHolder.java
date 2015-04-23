package org.jtwig.context;

public class RenderContextHolder {
    private static final ThreadLocal<RenderContext> current = new ThreadLocal<>();

    public static RenderContext set (RenderContext context) {
        current.set(context);
        return context;
    }

    public static RenderContext get () {
        return current.get();
    }
}
