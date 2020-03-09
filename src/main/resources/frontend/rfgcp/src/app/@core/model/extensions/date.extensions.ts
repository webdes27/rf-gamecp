interface Date {
   toBrazilianFormat(): string;
}

Date.prototype.toBrazilianFormat = function (): string {
   return getStringNumber(this.getDay()) + '/' +
      getStringNumber(this.getMonth()) + '/' +
      getStringNumber(this.getFullYear());
};

function getStringNumber(num: number) {
   return num < 10 ? `0${num}` : '' + num;
}

