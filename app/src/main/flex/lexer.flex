package com.example.practica1_compi.analizadores;

import java_cup.runtime.Symbol;
import com.example.practica1_compi.analizadores.*;
import java.util.*;
import com.example.practica1_compi.models.ErrorReporte;

%% //separador de area

%public
%unicode
%class Lexer
%cup
%line
%column

%init{

    errorList = new ArrayList<>();

%init}

/********************* macros ***************************************/

DIGITO = [0-9]
LETRA = [a-zA-Z]
ID = {LETRA}({LETRA}|{DIGITO}|_)*
ENTERO = {DIGITO}+
DECIMAL = {DIGITO}+"."{DIGITO}+
DECIMAL_INCOMPLETO = {DIGITO}+ "."
CADENA= \"[^\"]*\"
HEXCOLOR= "H"[0-9a-fA-F]{6}
ESPACIO = [ \t\r\n\f]+

ID_INVALIDO_NUM = ({DIGITO}+("."{DIGITO}+)?)({LETRA}|_)+({LETRA}|{DIGITO}|_)*
ID_INVALIDO_GUION = _({LETRA}|{DIGITO}|_)+

/***********************codigo del usuario ****************************/
%{
    //metodo para el manejo de errores
  private List<String> errorList;
  private List<ErrorReporte> errorReportes = new ArrayList<>();

  public List<ErrorReporte> getErrorReportes() {
      return errorReportes;
  }

  public List<String> getLexicalErrors(){
      return this.errorList;
  }

  //personalizacion del sym
  private Symbol symbol(int type) {
      return new Symbol(type, yyline + 1, yycolumn + 1, yytext());
  }

  private Symbol symbol(int type, Object value) {
      return new Symbol(type, yyline + 1, yycolumn +1, value);
  }

  private void error(String message) {
      int linea = yyline +1;
      int columna = yycolumn+1;
      String lexema = yytext();

      errorList.add("Error lexico en la linea: " + linea +", columna: " + columna + ": " + message);

      errorReportes.add(new ErrorReporte(lexema, linea, columna, "lexico", message));
  }

%}

%%

//comentario
"#".*                                       { /* provisionalmente se ignora */}

"%%%%"                                      { return symbol(sym.SEPARADOR_SECCION); }

//elementos de configuracion de diagramas

//instrucciones de configuracion
"%DEFAULT" |
"%COLOR_TEXTO_SI" |
"%COLOR_SI" |
"%FIGURA_SI" |
"%LETRA_SI" |
"%LETRA_SIZE_SI" |
"%COLOR_TEXTO_MIENTRAS" |
"%COLOR_MIENTRAS" |
"%FIGURA_MIENTRAS" |
"%LETRA_MIENTRAS" |
"%LETRA_SIZE_MIENTRAS" |
"%COLOR_TEXTO_BLOQUE" |
"%COLOR_BLOQUE" |
"%FIGURA_BLOQUE" |
"%LETRA_BLOQUE" |
"%LETRA_SIZE_BLOQUE"            { return symbol(sym.CONFIG); }

"ELIPSE" | "CIRCULO" | "PARALELOGRAMO" |
"RECTANGULO" | "ROMBO" |
"RECTANGULO_REDONDEADO"           { return symbol(sym.FIGURA); }

"ARIAL" | "TIMES_NEW_ROMAN" |
"COMIC_SANS" | "VERDANA"          { return symbol(sym.LETRA); }

{HEXCOLOR}                        { return symbol(sym.COLOR_HEX); }

{ID_INVALIDO_NUM}                 { error("Identificador invalido (comienza con numero): " + yytext());
                                        return symbol(sym.ERROR_IDENTIFICADOR_INVALIDO, yytext());}

{ID_INVALIDO_GUION}               { error("Identificador invalido (comienza con guion bajo): " + yytext());
                                        return symbol(sym.ERROR_IDENTIFICADOR_INVALIDO, yytext());}

//pseudocodigo
"INICIO"                            { return symbol(sym.INICIO); }
"FIN"                               { return symbol(sym.FIN); }
"VAR"                               { return symbol(sym.VAR); }
"SI"                                { return symbol(sym.SI); }
"ENTONCES"                          { return symbol(sym.ENTONCES); }
"FINSI"                             { return symbol(sym.FINSI); }
"MIENTRAS"                          { return symbol(sym.MIENTRAS); }
"HACER"                             { return symbol(sym.HACER); }
"FINMIENTRAS"                       { return symbol(sym.FINMIENTRAS); }
"MOSTRAR"                           { return symbol(sym.MOSTRAR); }
"LEER"                              { return symbol(sym.LEER); }

"=="                                { return symbol(sym.IGUALDAD); }
"!="                                { return symbol(sym.DISTINTO); }
"<="                                { return symbol(sym.MENOR_IGUAL); }
">="                                { return symbol(sym.MAYOR_IGUAL); }
"<"                                 { return symbol(sym.MENOR); }
">"                                 { return symbol(sym.MAYOR); }

"&&"                               { return symbol(sym.AND); }
"||"                               { return symbol(sym.OR); }
"!"                                { return symbol(sym.NOT_LOGICO); }

"+"                                 { return symbol(sym.SUMA); }
"-"                                 { return symbol(sym.RESTA); }
"*"                                 { return symbol(sym.MULT); }
"/"                                 { return symbol(sym.DIV); }

"="                                { return symbol(sym.OPERADOR_ASIGNACION); }

","                                { return symbol(sym.COMA); }
"|"                                { return symbol(sym.PIPE); }
"("                                { return symbol(sym.LPAREN); }
")"                                { return symbol(sym.RPAREN); }

{DECIMAL_INCOMPLETO}               { error("Decimal incompleto: " + yytext());
                                        return symbol(sym.ERROR_DECIMAL_INVALIDO); }

{DECIMAL}                          { return symbol(sym.NUMERO, Double.parseDouble(yytext())); }
{ENTERO}                           { return symbol(sym.NUMERO, Integer.parseInt(yytext())); }

{CADENA}                           { String cadena = yytext().substring(1, yytext().length()-1);
                                        return symbol(sym.CADENA); }

{ID}                               { return symbol(sym.IDENTIFICADOR, yytext()); }

{ESPACIO}                          { /* ignorar */ }

.                                   { error("Caracter no reconocido: " + yytext());
                                        return symbol(sym.ERROR); }

<<EOF>>                             { return new Symbol(sym.EOF); }