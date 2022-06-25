# ASD_4

Rozwiązanie każdego z zadań polega na:
1) Zaimplementowaniu struktury drzewa, która umożliwia efektywne
wykonywanie operacji
2) Przy implementacji należy wybrać drzewo AVL lub niezrównoważone
drzewo BST (połowa punktów do zdobycia)

UWAGA:

-zakładamy, że w prawym poddrzewie znajdują się elementy większe, a w lewym mniejsze

-przy implementowaniu usuwania węzła, który ma dwóch synów, należy w jego miejsce wstawić
najmniejszy element w jego prawym poddrzewie

-w drzewie nie dopuszcza się duplikatów


**Problem 1 - „Liczby rzeczywiste”**

Napisać program do zarządzania zbiorem liczb rzeczywistych nieujemnych (maksymalnie 8 cyfr
dziesiętnych przed i po przecinku). Program ma umożliwiać szybkie wykonywanie następujących
operacji:
a) wstawienie do zbioru nowej liczby
b) usunięcie ze zbioru zadanej liczby
c) wyszukanie w zbiorze danej liczby
d) znalezienie liczby liczb, których część całkowita jest równa zadanej przez użytkownika liczbie
e) wyświetlenie struktury drzewa wraz z elementami
f) wykonanie skryptu poleceń:
1) W x – wstaw x
2) U x – usuń x
3) S x – szukaj x (odpowiedź: TAK/NIE)
4) L x – wypisać, ile liczb posiada część całkowitą równą x


**Przykład**

Plik wejściowy: 

5 //liczba poleceń 

W 1,6 //wstaw liczbę 1,5 1 

W 1,8 //wstaw liczbę 1,8

S 2,5 //szukaj liczby 2,5

W 2,1 //wstaw liczbę 2,1

U 1,6 //usuń liczbę 1,6

L 2 //ile liczb posiada część całkowitą 2

Plik wyjściowy:

NIE //liczba 2,5 nie występuje w drzewie

//1 liczba posiada część całkowitą 2
