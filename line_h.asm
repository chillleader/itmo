.model small
.data
address dw 0
bitmask db 8
x1 dw 0 ; line starting point
x2 dw 640 ; max screen width
y dw 100 ; line starting point
color db 12
len dw 640 ; line length

.code
drawpixel proc near uses ax bx dx cx
    ; ?????????? ????????? ? ????????

    mov ax, y        ; select y
    mov dx, 80          ; 640 / 80 = 8 -> ???????? ? ?????
    mul dx        ; ax = y * 80 -> ???????? ? ?????? ?? y
    
    mov bx, x1
    mov cl, bl
    shr bx, 1               ; x // 8
    shr bx, 1
    shr bx, 1
    
    add bx, ax        ; x + y => ????????
    
    ; ?????? ??? ???
    
    and cl, 07h        ; bitmask mask
    xor cl, 07h
    mov ah, 01h
    shl ah, cl
    mov bitmask, ah
    
    ;????????? ????
    mov dx, 3CEh     
    mov al, 8           
    out dx, al    
    inc dx       
    mov al, bitmask           
    out dx, al         

    mov al, es:[bx]     

    mov dx, 3C4h      
    mov al, 2         
    out dx, al          
    inc dx          
    mov al, color     
    out dx, al        
    
    mov al, 0FFh    
    mov es:[bx], al  

    ret
    drawpixel endp

start:
; Init Adapter

mov ax, @data   
mov ds, ax   
mov ax, 12h        ; VGA 640x480x256
int 10h                 ; ?????????? BIOS

; Init offset

mov ax, 0A000h  
mov es, ax 

; next line draw fix
mov bx, x2       ; width bound
sub bx, x1         ; segment bound
cmp bx, len       ; segment <= length ?
jle cut

mov ax, len       ; it's ok
jmp e

cut:
    mov ax, bx        ; draw line only in segment
e:
    mov cx, ax

drawline: call drawpixel
    inc x1
    mov bx, x1
    cmp bx, x2
    jle drawline
;loop drawline

; ??????????

xor ax, ax  
int 16h

mov ax, 4C00h    
int 21h   
end start