Challenge name: Ret2Libc
Description:
```
Return to libc
With full canary
But no binary
So can you beat me?
```

### Chú thích
Thư mục player là các file đó sẽ được public cho người chơi
Thư mục docker có script buildnrun.sh dùng để build và chạy docker
Thư mục solution chứa mã nguồn và solve script

### Dữ liệu dưới đây sẽ không được công khai chung vs chall
Hint 1: Tại sao `*** stack smashing detected ***` nhưng vẫn chạy tiếp?
Hint 2: Tất cả là tại fork()
Hint 3: Ta đã biết được 3 ký tự cuối của `__libc_start_main_ret` rồi nhỉ?
