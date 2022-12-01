set terminal png  
set title "Comparaison"
set xlabel 'n' 
set ylabel 'ms' 
set output 'comparatif.png' 
set key top left
plot "graphStream.dat" t " Temps d'exécution  GraphStream" with linespoints ls 1,"naive.dat" t"Temps d'exécution  naive" with linespoints ls 2 ;