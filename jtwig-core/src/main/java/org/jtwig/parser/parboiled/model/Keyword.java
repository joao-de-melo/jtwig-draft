package org.jtwig.parser.parboiled.model;

public enum Keyword {
    INCLUDE("include"),
    SET("set"),
    BLOCK("block"),
    END_BLOCK("endblock"),
    IF("if"),
    END_IF("endif"),
    ELSE_IF("elseif"),
    ELSE("else"),
    FOR("for"),
    END_FOR("endfor"),
    IMPORT("import"),
    MACRO("macro"),
    END_MACRO("endmacro"),
    EXTENDS("extends"),
    EMBED("embed"),
    END_EMBED("endembed")
    ;

    private final String symbol;

    Keyword(String symbol) {
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return symbol;
    }
}
