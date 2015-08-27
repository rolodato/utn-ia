\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{conaiisi}
\LoadClass[a4paper, 12pt]{article}
\RequirePackage[a4paper, margin=2.5cm]{geometry}
\RequirePackage{multicol}
\RequirePackage{titling}
\RequirePackage{times}
\RequirePackage{authblk}
\RequirePackage[spanish]{babel}
\RequirePackage{titlesec}
\RequirePackage[utf8]{inputenc}
\RequirePackage[T1]{fontenc}
\RequirePackage[
	backend=bibtex,
	bibencoding=ascii,
	style=numeric,
	sorting=none
]{biblatex}
\renewcommand*{\bibfont}{\fontsize{10pt}{12pt}}

\date{}
\pagenumbering{gobble}

\setlength{\parindent}{0em}
\setlength{\parskip}{0em}

\titlespacing{\section}{0em}{1em}{0em}
\titlespacing{\subsection}{0em}{1em}{0em}

% Title
\renewcommand{\maketitlehooka}{\fontsize{16pt}{19.2pt}\bfseries}
% Author name
\renewcommand\Authfont{\fontsize{14}{16.8}\bfseries}
% Universidad Tecnológica Nacional, Facultad Regional Buenos Aires
\renewcommand\Affilfont{\fontsize{12pt}{14.4pt}\itshape\bfseries}

\titleformat*{\section}{\fontsize{12pt}{14.4pt}\bfseries}
\newcommand{\mainsection}[2]{%
  \section*{#1}
  \fontsize{12pt}{14.4pt}
  \selectfont
  #2
}

\titleformat*{\subsection}{\fontsize{10pt}{12pt}\bfseries}

\titleformat*{\paragraph}{\fontsize{12pt}{14.4pt}\itshape}

\titleformat*{\subsubsection}{\fontsize{12pt}{14.4pt}\itshape}

\newcommand{\smallsection}[2]{%
  \subsection*{#1}
  \fontsize{10pt}{12pt}
  \textit{#2}
}
