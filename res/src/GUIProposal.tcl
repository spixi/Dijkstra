#! /usr/bin/env wish
package require Tk

#Configure the main window
wm title . Flugauskunft

#Configure the widgets
button  .bt -text Berechnen -command {tk_messageBox -parent . -title Dummy -message Berechnung}

label     .la1 -text Abflughafen
listbox   .lb1 -selectmode single -height 5
scrollbar .sb1 -command [list .lb1 yview]

label     .la2 -text Zielflughafen
listbox   .lb2 -selectmode single -height 5
scrollbar .sb2 -command [list .lb2 yview]

#Fill in some dummy values
set l {Berlin Frankfurt Oslo Peking Rom Prag Moskau}

foreach i [lreverse $l] {
	.lb1 insert 0 $i
	.lb2 insert 0 $i
}

#Configure the geometry managers
grid .la1 .lb1 .sb1
grid .la2 .lb2 .sb2
grid .bt