# dash
Simple configurable link shortcut dashboard for nodejs

edit the config.edn file with your own links :)

I use it for my home network to add Kodi Corus Web UI and Transmision Web Client some only those two icon are available.

This repo is also an example of how awesome clojure.spec is :) all html is created in a Hiccup-like style that is speced and parsed with clojure.spec

### Building

first do this

> cp ./config.edn ./config_prod.edn

then run the build script

> ./scripts/build

This will build the whole a node runnable app and create a .tar.gz archive of the whole thing.

the archive will be under the name dash_build.tar.gz


the build script depends on leiningen and the tar utility

### cleaning up

run 
> ./scripts/clean

That runs 'lein clean' and deletes the build artifact (i.e. the dash_build.tar.gz file)

