# dash
Simple configurable link shortcut dashboard for nodejs

edit the config.edn file with your own links :)

I use it for my home network to add Kodi Chorus Web UI and Transmision Web Client some only those two icon are available.

This repo is also an example of how awesome clojure.spec is :) all html is created in a Hiccup-like style that is speced and parsed with clojure.spec

### Running the project

Start fighweel

> lein figwheel

Then start the node server

> node ./target/server_dev/dash.js ./config.edn

### Building

first do this

> cp ./config.edn ./config_prod.edn

then edit the ./config_prod.edn file and add all your links.

(this needs to be done once, in order to create all needed files for the build)


then run the build script (this should be done everytime you want to do a production build)

> ./scripts/build

This will build the whole a node runnable app and create a .tar.gz archive of the whole thing.

the archive will be under the name dash_build.tar.gz


the build script depends on leiningen and the tar utility

### cleaning up

run 
> ./scripts/clean

That runs 'lein clean' and deletes the build artifact (i.e. the dash_build.tar.gz file)

