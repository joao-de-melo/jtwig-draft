package org.jtwig.context.model;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import org.jtwig.render.Renderable;
import org.jtwig.render.model.OverrideRenderable;
import org.jtwig.resource.Resource;

import java.util.Map;

public class ResourceContext {
    private final Resource resource;
    private final Map<String, Macro> macros;
    private final Map<String, Renderable> blocks;
    private Optional<String> currentBlock = Optional.absent();

    public ResourceContext(Resource resource, Map<String, Macro> macros, Map<String, Renderable> blocks) {
        this.resource = resource;
        this.macros = macros;
        this.blocks = blocks;
    }

    public void register(String name, Macro macro) {
        macros.put(name, macro);
    }

    public Resource resource() {
        return resource;
    }

    public void register(String name, Renderable renderable) {
        this.currentBlock = Optional.of(name);
        if (blocks.containsKey(name)) {
            OverrideRenderable overrideRenderable = new OverrideRenderable(blocks.get(name))
                .overrideWith(renderable);

            blocks.put(name, overrideRenderable);
        } else {
            blocks.put(name, renderable);
        }
    }

    public ResourceContext merge(ResourceContext context) {
        blocks.putAll(context.blocks);
        return this;
    }

    public MacroContext macro() {
        return new MacroContext(macros);
    }

    public Optional<OverrideRenderable> currentBlock() {
        return currentBlock.transform(new Function<String, OverrideRenderable>() {
            @Override
            public OverrideRenderable apply(String input) {
                Renderable renderable = blocks.get(input);
                if (renderable instanceof OverrideRenderable) {
                    return (OverrideRenderable) renderable;
                }
                return new OverrideRenderable(renderable);
            }
        });
    }

    public Optional<Renderable> block(String blockName) {
        return Optional.fromNullable(blocks.get(blockName));
    }

    public void endBlock() {
        this.currentBlock = Optional.absent();
    }
}
