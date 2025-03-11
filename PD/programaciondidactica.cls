%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Clase didactica.cls
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{didactica}[2025/03/11 Clase didactica para programación docente]

% Se carga la clase base article con 12pt y para una sola cara
\LoadClass[12pt,oneside]{article}

% --------------------
% Paquetes básicos
% --------------------
\RequirePackage[utf8]{inputenc}      % Codificación de entrada (para pdfLaTeX)
\RequirePackage[T1]{fontenc}         % Codificación de fuente
\RequirePackage{geometry}            % Configuración de márgenes y papel
\geometry{
  a4paper,
  left=2.54cm,
  right=2.54cm,
  top=2.54cm,
  bottom=2.54cm
}
\RequirePackage{setspace}            % Interlineado
\doublespacing                       % Interlineado doble en el documento principal

% --------------------
% Fuente y apariencia
% --------------------
% Se usa el paquete helvet para aproximarse a la fuente Arial
\RequirePackage{helvet}
\renewcommand{\familydefault}{\sfdefault}

% --------------------
% Hipervínculos
% --------------------
\RequirePackage{hyperref}
\hypersetup{
  colorlinks=true,
  linkcolor=blue,
  urlcolor=blue,
  citecolor=blue
}

% --------------------
% Índice personalizado
% --------------------
\RequirePackage{imakeidx}
\makeindex
% La sintaxis \index[Entrada!subentradas]{Título que aparece} funcionará según lo definido en imakeidx

% --------------------
% Encabezados y pies de página
% --------------------
\RequirePackage{fancyhdr}
\pagestyle{fancy}
\fancyhead{} % Limpiar encabezados
\fancyfoot{} % Limpiar pies de página

% Comando para definir el nombre y apellidos del aspirante (se debe redefinir en el preámbulo del documento)
\newcommand{\aspirantenombre}{Nombre y Apellidos del Aspirante}

% Se coloca el nombre del aspirante en la cabecera de cada página (a la derecha)
\fancyhead[R]{\aspirantenombre}
% Número de página centrado en el pie de página
\fancyfoot[C]{\thepage}

% --------------------
% Definición de etiquetas (labels) con enlace clickable
% --------------------
% Contador para etiquetas "normales"
\newcounter{normallabel}
% Comando para crear una etiqueta normal; imprime el número y permite enlazar
\newcommand{\normalLabel}[1]{%
  \refstepcounter{normallabel}%
  \label{#1}%
  \hyperref[#1]{\thenormallabel}%
}

% Contador para etiquetas de legislación
\newcounter{legislacion}
% Comando para crear una etiqueta de legislación; se debe pasar como argumento el identificador y el nombre de la Ley
\newcommand{\legislacionLabel}[2]{%
  \refstepcounter{legislacion}%
  \label{#1}%
  \hyperref[#1]{#2}%
}

% --------------------
% Entorno para tablas con interlineado sencillo
% --------------------
\newenvironment{singletable}[1][htbp]{%
  \begin{singlespace}%
  \begin{table}[#1]%
}{%
  \end{table}%
  \end{singlespace}%
}

% --------------------
% Portada (Cover Page)
% --------------------
% Comando para definir la especialidad (se debe redefinir en el documento)
\newcommand{\especialidad}{Especialidad}

% Entorno para la portada. La portada incluye los datos de identificación y la firma.
% Se considera que la portada no se incluye en la numeración final del documento.
\newenvironment{portada}{%
  \thispagestyle{empty}%
  \begin{center}%
    \vspace*{2cm}%
    {\LARGE \bfseries Portada}\\[1cm]%
    {\large \aspirantenombre}\\[0.5cm]%
    {\large \especialidad}\\[2cm]%
  \end{center}%
  \noindent\rule{\textwidth}{1pt}\\[1cm]%
  \begin{flushright}
    Firma: \makebox[5cm]{\hrulefill}
  \end{flushright}%
  \newpage
  % Se reinicia la numeración a partir de la siguiente página
  \pagenumbering{arabic}
}{%
}

% --------------------
% Final de la clase
% --------------------
\endinput
