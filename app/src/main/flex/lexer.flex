import com.example.practica1_compi.models.Token;

%% //separador de area

/********* declaraciones de jflex ********************/
%public
%unicode
%class Lexer
//%standalone
%line
%column
%type com.example.practica1_compi.models.Token

%init{
/******* elementos dentro del constructor ************/

%init}

%{
/**************** codigo de usuario *******************/

%}

/********************* macros ***************************************/

DIGITO = [0-9]
LETRA = [a-zA-Z]
ID = {LETRA}({LETRA}|{DIGITO}|_)*
ENTERO = {DIGITO}+
DECIMAL = {DIGITO}+"."{DIGITO}+
CADENA= \"[^\"]*\"
HEXCOLOR= "H"[0-9a-fA-F]{6}
ESPACIO = [ \t\r\n\f]+

//errores en los identificadores
ID_INVALIDO_NUM = ({DIGITO}+("."{DIGITO}+)?)({LETRA}|_])+({LETRA}|{DIGITO}|_)*
ID_INVALIDO_GUION = _({LETRA}|{DIGITO}|_)+
//errores en decimales
DECIMAL_INCOMPLETO = {DIGITO}+"."

%% //separador de area

/******************* reglas lexicas *********************/

//comentario
"#".*                                       { /* provisionalmente se ignora */}

//separador de secciones
"%%%%"                                      { return new Token("separador de seccion", yytext(), yyline+1, yycolumn+1);}

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
"%LETRA_SIZE_BLOQUE"            { return new Token("CONFIG", yytext(), yyline+1, yycolumn+1); }


//figuras
"ELIPSE" | "CIRCULO" | "PARALELOGRAMO" | "RECTANGULO" |
"ROMBO" | "RECTANGULO_REDONDEADO"           { return new Token("FIGURAS", yytext(), yyline+1, yycolumn+1);}

//tipos de letra
"ARIAL" | "TIMES_NEW_ROMAN" | "COMIC_SANS" | "VERDANA"      { return new Token("LETRAS", yytext(), yyline+1, yycolumn+1);}

//color hexadecimal
{HEXCOLOR}                                  { return new Token("color hexadecimal", yytext(), yyline+1, yycolumn+1);}

//errores de los identificadores
{ID_INVALIDO_NUM}                           { return new Token("ERROR_IDENTIFICADOR_INVALIDO", yytext(), yyline+1, yycolumn+1);}
{ID_INVALIDO_GUION}                         { return new Token("ERROR_IDENTIFICADOR_INVALIDO", yytext(), yyline+1, yycolumn+1);}

//elementos de pseudocodigo

//palabras reservadas
"INICIO" |
"FIN" |
"VAR" |
"SI" |
"ENTONCES" |
"FINSI" |
"MIENTRAS" |
"HACER" |
"FINMIENTRAS" |
"MOSTRAR" |
"LEER"                                  { return new Token("PALABRA RESERVADA", yytext(), yyline+1, yycolumn+1);}

//operadores relaciones
"==" | "!=" | "<=" | ">="               { return new Token("OPERADOR RELACIONAL", yytext(), yyline+1, yycolumn+1);}
"<" | ">"                               { return new Token("OPERADOR RELACIONAL", yytext(), yyline+1, yycolumn+1);}

//operador logico
"&&" | "||"                             { return new Token("OPERADOR LOGICO", yytext(), yyline+1, yycolumn+1);}
"!"                                     { return new Token("NOT_LOGICO", yytext(), yyline+1, yycolumn+1);}

//operadores aritmeticos
"+" | "-" | "*" | "/"                   { return new Token("OPERADOR ARITMETICO", yytext(), yyline+1, yycolumn+1);}

//asignacion
"="                                     { return new Token("OPERADOR ASIGNACION", yytext(), yyline+1, yycolumn+1);}

//simbolo
","                                     { return new Token("COMA", yytext(), yyline+1, yycolumn+1);}
"|"                                     { return new Token("PIPE", yytext(), yyline+1, yycolumn+1);}
"("                                     { return new Token("LPAREN", yytext(), yyline+1, yycolumn+1);}
")"                                     { return new Token("RPAREN", yytext(), yyline+1, yycolumn+1);}

//literales numericas
{DECIMAL_INCOMPLETO}                    { return new Token("ERROR_DECIMAL_INVALIDO", yytext(), yyline+1, yycolumn+1); }
{DECIMAL}                               { return new Token("DEDIMAL", yytext(), yyline+1, yycolumn+1);}
{ENTERO}                                { return new Token("ENTERO", yytext(), yyline+1, yycolumn+1);}

//cadena
{CADENA}                                { return new Token("CADENA", yytext(), yyline+1, yycolumn+1);}

//identificadores
{ID}                                    { return new Token("IDENTIFICADOR", yytext(), yyline+1, yycolumn+1);}

//espacio
{ESPACIO}                               { /*no hace nada*/ }

//error lexico
.                                       { return new Token("ERROR", yytext(), yyline+1, yycolumn+1);}

<<EOF>>                                 { return null; }