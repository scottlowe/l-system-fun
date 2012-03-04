(ns l-system.grammars)

(def tree-cmd-map {\F :forward
                   \+ :left
                   \- :right
                   \[ :push
                   \] :pop})

(def axial-tree-a {:start     [\F]
                   :rules     {\F "F[+F]F[-F]F"}
                   :angle     25.7
                   :constants #{\F \+ \- \[ \]}
                   :cmd-map   tree-cmd-map})

(def axial-tree-c {:start     [\F]
                   :rules     {\F "FF-[-F+F+F]+[+F-F-F]"}
                   :angle     22.5
                   :constants #{\F \+ \- \[ \]}
                   :cmd-map   tree-cmd-map})

(def axial-tree-d {:start     [\X]
                   :rules     {\X "F[+X]F[-X]+X"
                               \F "FF"}
                   :angle     20
                   :constants #{\F \+ \- \[ \]}
                   :cmd-map   tree-cmd-map})

(def axial-tree-f {:start     [\X]
                   :rules     {\X "F-[[X]+X]+F[+FX]-X"
                               \F "FF"}
                   :angle     22.5
                   :constants #{\F \+ \- \[ \]}
                   :cmd-map   tree-cmd-map})

(def dragon-curve {:constants #{\F \+ \-}
                   :start     [\F \X]
                   :rules     {\X "X+YF"
                               \Y "FX-Y"}
                   :angle      90
                   :cmd-map   {\F :forward
                               \+ :left
                               \- :right}})

(def sierpinski-triangle {:constants #{\+ \-}
                          :start     [\A]
                          :rules     {\A "B-A-B"
                                      \B "A+B+A"}
                          :angle      60
                          :cmd-map   {\A :forward
                                      \B :forward
                                      \+ :left
                                      \- :right}})

