export declare type Filter = (src: string, dst: string) => boolean;
export declare type Processor = (src: string, dst: string) => void;
export declare function recurse(src: string, dst: string, autoCreate: boolean, filter: Filter, processor: Processor): void;
