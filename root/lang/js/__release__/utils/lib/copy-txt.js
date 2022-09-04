"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.copyTxt = void 0;
const path = require("path");
const fs = require("fs");
const recurse_1 = require("./recurse");
function copyTxt(src, dst) {
    (0, recurse_1.recurse)(src, dst, true, src => fs.statSync(src).isDirectory() || path.extname(src) === '.txt', (src, dst) => fs.createReadStream(src).pipe(fs.createWriteStream(dst)));
}
exports.copyTxt = copyTxt;
