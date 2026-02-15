%% //separador de area

/********* declaraciones de jflex ********************/
%public
%unicode
%class Lexer
%standalone
%line
%column

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

%% //separador de area

/******************* reglas lexicas *********************/

//comentario
"#".*                                       { System.out.println("comentario encontrado");}

//separador de secciones
"%%%%"                                      { System.out.println("Separador encontrado");}

//elementos de configuracion de diagramas

//instrucciones de configuracion
"%DEFAULT"                     { System.out.println("config: DEFAULT"); }
"%COLOR_TEXTO_SI"              { System.out.println("config: COLOR_TEXTO_SI"); }
"%COLOR_SI"                    { System.out.println("config: COLOR_SI"); }
"%FIGURA_SI"                   { System.out.println("config: FIGURA_SI"); }
"%LETRA_SI"                    { System.out.println("config: LETRA_SI"); }
"%LETRA_SIZE_SI"               { System.out.println("config: LETRA_SIZE_SI"); }
"%COLOR_TEXTO_MIENTRAS"        { System.out.println("config: COLOR_TEXTO_MIENTRAS"); }
"%COLOR_MIENTRAS"              { System.out.println("config: COLOR_MIENTRAS"); }
"%FIGURA_MIENTRAS"             { System.out.println("config: FIGURA_MIENTRAS"); }
"%LETRA_MIENTRAS"              { System.out.println("config: LETRA_MIENTRAS"); }
"%LETRA_SIZE_MIENTRAS"         { System.out.println("config: LETRA_SIZE_MIENTRAS"); }
"%COLOR_TEXTO_BLOQUE"          { System.out.println("config: COLOR_TEXTO_BLOQUE"); }
"%COLOR_BLOQUE"                { System.out.println("config: COLOR_BLOQUE"); }
"%FIGURA_BLOQUE"               { System.out.println("config: FIGURA_BLOQUE"); }
"%LETRA_BLOQUE"                { System.out.println("config: LETRA_BLOQUE"); }
"%LETRA_SIZE_BLOQUE"           { System.out.println("config: LETRA_SIZE_BLOQUE"); }


//figuras
"ELIPSE" | "CIRCULO" | "PARALELOGRAMO" | "RECTANGULO" |
"ROMBO" | "RECTANGULO_REDONDEADO"           { System.out.println("config: figura");}

//tipos de letra
"ARIAL" | "TIMES_NEW_ROMAN" | "COMIC_SANS" | "VERDANA"      { System.out.println("config: letra");}

//color hexadecimal
{HEXCOLOR}                                  { System.out.println("config: color hex");}

//elementos de pseudocodigo

//palabras reservadas
"INICIO"                                { System.out.println("palabra reservada");}
"FIN"                                   { System.out.println("palabra reservada");}
"VAR"                                   { System.out.println("palabra reservada");}
"SI"                                    { System.out.println("palabra reservada");}
"ENTONCES"                              { System.out.println("palabra reservada");}
"FINSI"                                 { System.out.println("palabra reservada");}
"MIENTRAS"                              { System.out.println("palabra reservada");}
"HACER"                                 { System.out.println("palabra reservada");}
"FINMIENTRAS"                           { System.out.println("palabra reservada");}
"MOSTRAR"                               { System.out.println("palabra reservada");}
"LEER"                                  { System.out.println("palabra reservada");}

//operadores relaciones
"==" | "!=" | "<=" | ">="               { System.out.println("operador relacional encontrado");}
"<" | ">"                               { System.out.println("operador relacional encontrado");}

//operador logico
"&&" | "||" | "!"                       { System.out.println("operador logico encontrado");}

//operadores aritmeticos
"+" | "-" | "*" | "/"                   { System.out.println("operador aritmetico encontrado");}

//asignacion
"="                                     { System.out.println("operador asignacion encontrado");}

//simbolo
","                                     { System.out.println("coma encontrada");}
"|"                                     { System.out.println("pipe encontrado");}
"(" | ")"                               { System.out.println("parentesis encontrado");}

//literales numericas
{DECIMAL}                               { System.out.println("Decimal encontrado");}
{ENTERO}                                { System.out.println("entero encontrado");}

//cadena
{CADENA}                                { System.out.println("cadena encontrada");}

//identificadores
{ID}                                    { System.out.println("Identificador encontrado");}

//espacio
{ESPACIO}                               { /*ignorar*/ }

//error lexico
.                                       { System.out.println("Error lexico: caracter ' " + yytext() + " ' en la linea " + (yyline+1) + ", columna " + (yycolumn+1));}

<<EOF>>                                 { /*fin del archivo*/}