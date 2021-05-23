
Berikut untuk instrumental testnya , kurang lebih flownya masih sama seperti sebelumnya

**Test case berlaku pada saat apliakasi pertama kali diinstall  , karena akan berpengaruh dengan jumlah favorit item jika test ke 2 dst

1.Test dimulai dengan funloadmovies() , pertama aplikasi akan membuka activity home dan menjalankan fragment movies , disini pertama tama aplikasi akan meload beberapa data list movies dari themoviedb menggunakan coroutine + retrofit + idling resource

2.Setelah data terload semua maka aplikasi akan mengecek apakah homvp rv now playing , top rated , dan upcoming terload dengan baik dengan cara menscroll dari item nomor 1 sampe 5 * untuk vp sampai last item

3.Setelah itu aplikasi akan membuka detail top movies dari item yang sedang tampil dalam viewpager

4.Di detail aplikasi akan menscroll setiap detail movies *tapi karena recomendation & similar movies tidak selalu ada pada item movies jadi di detail movies dia tidak memastikan item tersebut terload / tidak hanya scrolling detailnya saja

5.Di detail movies juga aplikasi akan mencoba add dan remove movies from favorit sebanyak 3x *add remove add

6.Setelah itu dia akan back ke home , ganti ke fragment tv series lalu mengulangi proses nomor 2-5 seperti movies

7.Back to home dan ganti ke fragment favorit setelah itu dia akan mengecek apakah rv favorit tv dan movies tampil 

8.Aplikasi akan membuka detail movies yang ada di favorit back to home lalu buka detail tv