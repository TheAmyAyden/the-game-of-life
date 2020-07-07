(define (empty-board)
  (make-vector 50 (make-vector 100 0)))

(define (make-indexes n)
  (define (iter i n)
    (if (>= i n)
        '()
        (cons i (iter (1+ i) n))))
  (iter 0 n))

(define (set-square x y board)
  (vector-map
   (lambda (row i)
     (vector-map
      (lambda (spot j) (if (and (= y i) (= x j))
                           1
                           spot))
      row
      (list->vector (make-indexes (vector-length row)))))
   board
   (list->vector (make-indexes (vector-length board)))))

(define (get-row board n)
  (vector-ref board n))

(define (get-column row n)
  (vector-ref row n))

(define (get-spot board x y)
  (get-column (get-row board y) x))

(define (draw-row row)
  (do ((x 0 (1+ x))) ((>= x (vector-length row)))
    (if (= (vector-ref row x) 0)
        (display "  ")
        (display " *")))
  (display "|")
  (newline))

(define (draw-board board)
  (newline)
  (do ((x 0 (1+ x))) ((>= x (vector-length (vector-ref board 0))))
    (display " _"))
  (newline)
  (do ((x 0 (1+ x))) ((>= x (vector-length board)))
    (draw-row (vector-ref board x))))

(define (count-neighbors y x board)
  (let ((result 0))
    (do ((i -1 (1+ i))) ((> i 1))
      (do ((j -1 (1+ j))) ((> j 1))
        (when
            (and
             (>= (+ x i) 0)
             (>= (+ y j) 0)
             (< (+ x i) (vector-length (vector-ref board 0)))
             (< (+ y j) (vector-length board))
             (= 1 (get-spot board (+ i x) (+ j y)))
             (or
              (not (zero? i))
              (not (zero? j))))
          (set! result (1+ result)))))
    result))

(define (count-neighbors-with-wrap-around y x board)
  (let ((result 0) (width (vector-length (get-row board 0))) (height (vector-length board)))
    (do ((i -1 (1+ i))) ((> i 1))
      (do ((j -1 (1+ j))) ((> j 1))
        (when
            (and
             (= 1 (get-spot board (mod (+ i x) width) (mod (+ j y) height)))
             (or
              (not (zero? i))
              (not (zero? j))))
          (set! result (1+ result)))))
    result))

;;(get-spot (set-square 4 2 (empty-board)) 4 2)
;;(count-neighbors 0 0 (set-square 1 1 (set-square 0 1 (empty-board))))

(define (update board)
  (vector-map
   (lambda (row i)
     (vector-map
      (lambda (spot j)
        (let ((neighbors (count-neighbors-with-wrap-around i j board)))
          (cond
           [(< neighbors 2) 0]
           [(= neighbors 2) spot]
           [(= neighbors 3) 1]
           [(> neighbors 3) 0])))
      row
      (list->vector (make-indexes (vector-length row)))))
   board
   (list->vector (make-indexes (vector-length board)))))

(define (loop board)
  (draw-board board)
  (sleep (make-time 'time-duration 10000000 0))
  (loop (update board)))

(define (set-square-list square-list board)
  (if (null? square-list)
      board
      (set-square-list (cdr square-list) (set-square (caar square-list) (cadar square-list) board))))

(define (draw-stoplight x y board)
  (set-square-list (list (list x y) (list (1- x) y) (list (1+ x) y) (list x (1+ y))) board))
;;  (set-square x y
;;    (set-square (1- x) y
;;      (set-square (1+ x) y
;;        (set-square x (1+ y) board)))))

(define (draw-stoplight2 x y board)
  (set-square x y
    (set-square (1- x) y
      (set-square (1+ x) y
        (set-square (1+ x) (1+ y)
          (set-square (1- x) (1+ y)
            (set-square x (1- y)
              (set-square x (1+ y) board))))))))

(define (draw-glider-gun x y board)
  (set-square-list '((26 1) (26 2) (26 6) (26 7) (24 2) (24 6) (23 3) (23 5)
                     (22 3) (22 5) (22 4) (23 4)
                     (36 3) (36 4) (37 3) (37 4)
                     (19 6) (18 5) (18 6) (18 7) (17 4) (17 8) (16 6) (15 3)
                     (15 9) (14 3) (14 9) (13 4) (13 8) (12 5) (12 6) (12 7)
                     (2 5) (2 6) (3 5) (3 6))
    board))

;;(loop (draw-stoplight2 5 5 (draw-stoplight 15 5 (draw-stoplight 5 15 (draw-stoplight2 15 15 (draw-stoplight2 25 5 (draw-stoplight 25 15 (draw-stoplight2 25 25 (draw-stoplight2 5 25 (draw-stoplight 15 25 (empty-board)))))))))))

;;(loop (draw-stoplight 5 20 (draw-glider-gun 0 0 (empty-board))))

(define (set-random-squares n board)
  (if (zero? n)
      board
      (set-random-squares
       (1- n)
       (set-square
        (random (vector-length (vector-ref board 1)))
        (random (vector-length board))
        board))))

(loop (set-random-squares 2500 (empty-board)))
