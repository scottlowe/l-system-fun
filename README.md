L-system fun
============

What is it?
----------
To improve my Clojure skills, I decided to tackle a small self-contained problem with results that are quick and fun.
I was surprised to discover how little code was required to satisfy my goal of generating and plotting
[L-system] data.

As a learning excercise, the code is probably of little practical use to you, unless you are interested in looking at Clojure code.

Usage
-----

If you have Leiningen installed, you probably already know the drill:

    lein deps
    lein repl

Once in the REPL you can generate an L-system pattern by specfiying the
grammar name and the nth generation that you want:

    l-system.display=> (grow dragon-curve 3)
    [:F :X :+ :Y :F :+ :F :X :- :Y :F :+ :F :X :+ :Y :F :- :F :X :- :Y :F]

To view graphical output of applets that plot using the constants (commands) of these L-systems:

    (run dragon-curve-app)
    (stop dragon-curve-app)

Other applets plot the koch curve (koch-curve-app) and sierpinski triangle (sierpinski-app).
The applets are not parameterized but of fixed iteration; such flexibility is beyond the intentions
of this code excercise... but of course you can open up the code and directly edit the applet definitions.

License
-------

Copyright (C) 2011 Scott Lowe

Licensed under the terms of the MIT License

[L-system]: http://en.wikipedia.org/wiki/L-system
