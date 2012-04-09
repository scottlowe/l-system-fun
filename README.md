L-system fun
============

What is it?
----------
To improve my Clojure skills, I decided to tackle a small self-contained problem with results that are quick and fun.
I was surprised to discover how little code was required to satisfy my goal of generating and plotting
[L-system] data.

More specifically, I wanted to generate axial trees, mostly because they are beautiful to look at, but also
because it requires the additional step of implementing a Bracketed OL-system, since the definition of tree L-systems
does not specify the data structure for representing axial trees.

I've blogged about this code here: [http://www.scottlowe.eu/blog/2012/03/04/l-system-axial-trees-with-clojure/](http://www.scottlowe.eu/blog/2012/03/04/l-system-axial-trees-with-clojure/)

The code is probably of little practical re-use value, but you are free to do what you like with it.

Usage
-----

If you have Leiningen installed, you probably already know the drill:

    lein deps
    lein repl

Once in the REPL you can generate an L-system pattern by specfiying the
grammar name and the nth generation that you want:

    l-system.display=> (gen-commands dragon-curve 5)
    "FX+YF+FX-YF+FX+YF-FX-YF+FX+YF+FX-YF-FX+YF-FX-YF+FX+YF+FX-YF+FX+YF-FX-YF-FX+YF+FX-YF-FX+YF-FX-YF"
    
... or for an axial tree:

    l-system.display=> (gen-commands axial-tree-f 2)
    "FF-[[F-[[X]+X]+F[+FX]-X]+F-[[X]+X]+F[+FX]-X]+FF[+FFF-[[X]+X]+F[+FX]-X]-F-[[X]+X]+F[+FX]-X"

To view graphical output of applets that plot using the constants (commands) of these L-systems:

    l-system.display=> (display tree-f-applet)

Other applets include the dragon-curve (display dragon-applet). Check src/l_system/display.clj for more applet definitions.


License
-------

Copyright (C) 2011 Scott Lowe

Licensed under the terms of the MIT License

[L-system]: http://en.wikipedia.org/wiki/L-system
