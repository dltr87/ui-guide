#!/usr/bin/env bash

. ./scripts/_prepare.sh

# === Find out where is stargazer
sgz_src_config=sgz_src.txt
cat ${sgz_src_config}
if [ ! -f ${sgz_src_config} ]; then
  echo "Please enter the absolute path to stargazer on your local:"
  read sgz_src
  echo ${sgz_src} > ${sgz_src_config}
else
  read -r sgz_src < ${sgz_src_config}
  echo "Found stargazer at ${sgz_src}."
fi

# === Link Config
echo "[info] Linking Config: .scalafmt.conf"
ln -sf ${sgz_src}/.scalafmt.conf ./

# === Link CSS

sgz_css=${sgz_src}/gondor/webResources/src/main/assets/stylesheets

echo "[info] Linking CSS: Tachyons ..."
ln -sf ${sgz_css}/tachyons ${core_css}/

echo "[info] Linking CSS: Base ..."
ln -sf ${sgz_css}/base/_base.scss ${core_css}/

# === Link JS (Style and Component)

sgz_js=${sgz_src}/platform/stargazerJs/src/main/scala/anduin

# Style
echo "[info] Linking JS: anduin.style ..."
ln -sf ${sgz_js}/style ${core_js}/

# JS Dependency (For Component)
echo "[info] Linking JS: dependencies for anduin.component ..."
sgz_js_dep=${sgz_src}/platform/stargazerJSDependency/src/main/scala/anduin/scalajs
deps=(
  react/ReactDom.scala
)
for name in ${deps[*]}
do
    echo "[info] - ${name} ..."
    pkg="$(echo ${name} | rev | cut -d"/" -f2- | rev)"
    mkdir -p ${core_js}/scalajs/${pkg}
    ln -sf ${sgz_js_dep}/${name} ${core_js}/scalajs/${name}
done

# Component
echo "[info] Linking JS: anduin.component ..."
components=(
  container/Table.scala
  portal/LegacyPortal.scala
  portal/ModalHeader.scala
  portal/PortalWrapper.scala
  portal/ModalBody.scala
  portal/Modal.scala
  portal/Position.scala
  portal/ModalFooter.scala
  portal/Popover.scala
  portal/Status.scala
  portal/ModalFooterWCancel.scala
  portal/Portal.scala
  portal/Tooltip.scala
  button/Button.scala
  button/ButtonStyle.scala
  input/FileInputButton.scala
  icon/IconAcl.scala
  util/EventUtils.scala
)
for name in ${components[*]}
do
    echo "[info] - ${name} ..."
    pkg="$(echo ${name} | rev | cut -d"/" -f2- | rev)"
    mkdir -p ${core_js}/component/${pkg}
    ln -sf ${sgz_js}/component/${name} ${core_js}/component/${name}
done

echo "[success] Successfully linked"