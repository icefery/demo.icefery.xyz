"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.recurse = void 0;
const fs = require("fs");
const path = require("path");
const log4js = require("log4js");
const logger = log4js.getLogger('recurse');
logger.level = 'info';
function recurse(src, dst, autoCreate, filter, processor) {
    if (filter(src, dst)) {
        const stats = fs.statSync(src);
        if (stats.isDirectory()) {
            if (autoCreate) {
                try {
                    fs.accessSync(dst, fs.constants.F_OK);
                }
                catch (e) {
                    fs.mkdirSync(dst, { recursive: true });
                    logger.info(`[${dst}] created.`);
                }
            }
            fs.readdirSync(src).forEach(sub => recurse(path.join(src, sub), path.join(dst, sub), autoCreate, filter, processor));
        }
        else if (stats.isFile()) {
            processor(src, dst);
        }
    }
    else {
        logger.info(`[${src}] skipped.`);
    }
}
exports.recurse = recurse;
